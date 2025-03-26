package com.shariarunix.composestripeimplementation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shariarunix.composestripeimplementation.models.StripeCustomer
import com.shariarunix.composestripeimplementation.models.StripeCustomerEphemeral
import com.shariarunix.composestripeimplementation.models.StripePaymentIntent
import com.shariarunix.composestripeimplementation.ui.theme.Constant
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

class PaymentViewModel() : ViewModel() {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val _clientSecret = MutableStateFlow<String?>(null)
    val clientSecret: StateFlow<String?> = _clientSecret

    var customerConfiguration : PaymentSheet.CustomerConfiguration? = null

    suspend fun initPaymentConfiguration(amount: Double) {
        val stripeCustomer: StripeCustomer? = createStripeCustomer()
        val stripeCustomerEphemeral: StripeCustomerEphemeral? = createCustomerEphemeralKey(stripeCustomer?.id)

        customerConfiguration = PaymentSheet.CustomerConfiguration(
            id = stripeCustomer?.id.toString(),
            ephemeralKeySecret= stripeCustomerEphemeral?.secret.toString(),
        )

        val stripePaymentIntent: StripePaymentIntent? = createPaymentIntent(
            amountToPay = (amount * 100).toInt(),
            currency = (stripeCustomer?.currency) ?: "usd",
            customer = (stripeCustomer?.id).toString(),
        )

        Log.e("SHARIAR", stripePaymentIntent.toString())
        _clientSecret.value = stripePaymentIntent?.clientSecret
        Log.e("SHARIAR", _clientSecret.value.toString())
    }

    @OptIn(InternalAPI::class)
    private suspend fun createPaymentIntent(amountToPay: Int, currency: String, customer: String): StripePaymentIntent? {
        /**
         *  Create Payment Intent
         *  API End Point : https://api.stripe.com/v1/payment_intents
         *  HTTP Method : POST
         */
        return try {
            var response: StripePaymentIntent? = client.post(urlString = "${Constant.STRIPE_API_BASE_URL}/payment_intents") {
                header("Authorization", "Bearer ${Constant.SECRET_KEY}")
                contentType(ContentType.Application.FormUrlEncoded)
                body = FormDataContent(Parameters.build {
                    append("amount", amountToPay.toString())
                    append("currency", currency)
                    append("customer", customer)
                    append("payment_method_types[]", "card")
                })
            }.body()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @OptIn(InternalAPI::class)
    private suspend fun createCustomerEphemeralKey(customerId: String?): StripeCustomerEphemeral? {
        /**
         *  Create Customer Ephemeral Key
         *  API End Point : https://api.stripe.com/v1/ephemeral_keys
         *  HTTP Method : POST
         */
        return try {
            var response: StripeCustomerEphemeral? = client.post(urlString = "${Constant.STRIPE_API_BASE_URL}/ephemeral_keys") {
                header("Authorization", "Bearer ${Constant.SECRET_KEY}")
                header("Stripe-Version", "2025-02-24.acacia")
                contentType(ContentType.Application.FormUrlEncoded)
                body = FormDataContent(Parameters.build {
                    append("customer", customerId.toString())
                })
            }.body()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun createStripeCustomer(): StripeCustomer? {
        /**
         *  CREATE Customer
         *  API End Point : https://api.stripe.com/v1/customers
         *  HTTP Method : POST
         */
        return try {
            var response: StripeCustomer? = client.post(urlString = "${Constant.STRIPE_API_BASE_URL}/customers") {
                header("Authorization", "Bearer ${Constant.SECRET_KEY}")
                contentType(ContentType.Application.FormUrlEncoded)
            }.body()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}