package com.techmihirnaik.mergeroommate.model

data class OrderItem(
    var imageReference: String = "",
    var name: String = "",
    var place: String = "",
    var date: Long = 0L,
    var services: String = "",
    var category: String = ""
)
