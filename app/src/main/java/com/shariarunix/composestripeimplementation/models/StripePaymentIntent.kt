package com.shariarunix.composestripeimplementation.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StripePaymentIntent(
    @SerialName("client_secret") var clientSecret: String? = null
)
