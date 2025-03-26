package com.shariarunix.composestripeimplementation.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StripeCustomer(
    @SerialName("id") var id: String? = null,
    @SerialName("address") var address: String? = null,
    @SerialName("balance") var balance: Double? = null,
    @SerialName("created") var created: Long? = null,
    @SerialName("currency") var currency: String? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("phone") var phone: String? = null,
    @SerialName("shipping") var shipping: String? = null,
)