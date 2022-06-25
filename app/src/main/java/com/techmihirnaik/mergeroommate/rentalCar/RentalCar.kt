package com.techmihirnaik.mergeroommate.rentalCar

data class RentalCar(
    var pickupLocation: String = "",
    var date: String = "",
    var time: String = "",
    var carType: String = "",
    var person: Int = 1,
)
