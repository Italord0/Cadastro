package com.github.italord0.cadastro.util

import com.github.italord0.cadastro.model.User
import java.util.*

object SampleUsers {
    val users = (1..500).map {
        User(
            it,
            "José $it",
            "jose$it@test$it.com",
            dateOfBirth = Date(System.currentTimeMillis() + (5000000000 * it)),
            balance = 50.0 + it
        )
    }
}