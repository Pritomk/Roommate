package com.techmihirnaik.mergeroommate.model

import android.net.Uri

data class Profile(
    var imageUri: Uri = Uri.parse("https://roommate.techmihirnaik.in/static/roommate/images/ROOMMATE-logo-black.png"),
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var number: String = ""
    )
