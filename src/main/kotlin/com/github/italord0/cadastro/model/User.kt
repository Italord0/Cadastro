package com.github.italord0.cadastro.model

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val dateOfBirth: Date,
    val balance: Double,
    val formattedDate: String = SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth),
    val formattedBalance: String = NumberFormat.getCurrencyInstance().format(balance)
)
