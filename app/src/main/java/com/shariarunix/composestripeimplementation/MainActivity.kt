package com.shariarunix.composestripeimplementation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shariarunix.composestripeimplementation.ui.icons.Heart
import com.shariarunix.composestripeimplementation.ui.theme.ComposeStripeImplementationTheme
import com.shariarunix.composestripeimplementation.ui.theme.Constant
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PaymentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeStripeImplementationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val context = LocalContext.current
                    val scope = rememberCoroutineScope()
                    val clientSecret = viewModel.clientSecret.collectAsState()
                    val paymentSheet = rememberPaymentSheet(::onPaymentSheetResult)

                    LaunchedEffect(clientSecret.value) {
                        clientSecret.value?.let { clientSecret ->
                            PaymentConfiguration.init(context, Constant.PUBLISHABLE_KEY)

                            paymentSheet.presentWithPaymentIntent(
                                clientSecret,
                                PaymentSheet.Configuration(
                                    merchantDisplayName = "iceBox Technology",
                                    customer = viewModel.customerConfiguration,
                                    googlePay = PaymentSheet.GooglePayConfiguration(
                                        environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
                                        countryCode = "US",
                                        currencyCode = "USD",
                                    ),
                                )
                            )
                        }
                    }

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Button(onClick = {
                                scope.launch {
                                    viewModel.initPaymentConfiguration(amount = 80.0)
                                }
                            }) {
                                Text(text = "Payment Sheet")
                                Icon(
                                    imageVector = Heart,
                                    tint = MaterialTheme.colorScheme.error,
                                    contentDescription = "Heart"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult){
            is PaymentSheetResult.Completed -> {
                Toast.makeText(this@MainActivity, "Payment Completed", Toast.LENGTH_SHORT).show()
            }

            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this@MainActivity, "Payment Canceled", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this@MainActivity, "Payment Failed ${paymentSheetResult.error.message}", Toast.LENGTH_SHORT).show()
            }
        }
        Log.e("SHARIAR", paymentSheetResult.toString())
    }
}