package com.techmihirnaik.mergeroommate.dao

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.techmihirnaik.mergeroommate.model.Profile

class ProfileDao {
    var profileData: MutableLiveData<Profile> = MutableLiveData()
    val profile = Profile()
    private val uid = Firebase.auth.currentUser?.uid

    fun imageUpload(imageUri: Uri) {
        val storage = Firebase.storage
        storage.reference.child("profile-pic/$uid").putFile(imageUri)
    }

    fun getPostImage() {
        val storage = Firebase.storage
        val reference = storage.reference.child("profile-pic/$uid")
        reference.downloadUrl.addOnSuccessListener {
            profile.imageUri = it
            profileData.value = profile
        }.addOnFailureListener { e ->
            profileData.value = profile
        }
    }
}