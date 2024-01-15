package com.github.italord0.cadastro.model

import java.text.NumberFormat
import java.text.SimpleDateFormat
import javax.persistence.*

@Entity
@Table(name = "User")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val name: String,
    val email: String,
    val dateOfBirth: Long,
    val balance: Double,
    val formattedDate: String = SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth),
    val formattedBalance: String = NumberFormat.getCurrencyInstance().format(balance)
)
