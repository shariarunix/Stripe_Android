package com.shariarunix.composestripeimplementation.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class StripeCustomerEphemeral(

    @SerialName("id") var id: String? = null,
    @SerialName("created") var created: Int? = null,
    @SerialName("expires") var expires: Int? = null,
    @SerialName("livemode") var livemode: Boolean? = null,
    @SerialName("secret") var secret: String? = null

)