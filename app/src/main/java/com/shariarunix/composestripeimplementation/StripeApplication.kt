package com.shariarunix.composestripeimplementation

import android.app.Application
import com.stripe.android.PaymentConfiguration

class StripeApplication() : Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51R62C0GsJNy3weJ5fGvqo4fC5kxGZnHSk0JaVLAKevb89pEsCScf43n9vJK6gT8HwaYO8axfz6COVd0RPwWg6ygi00FXuG2u3w"
        )
    }
}