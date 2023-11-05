package com.example.count_out.entity

interface Round {
    val set: Set
    val amount: Int
    val beforeTime: Int
    val afterTime: Int
    val restTime: Int
    val open: Boolean
}