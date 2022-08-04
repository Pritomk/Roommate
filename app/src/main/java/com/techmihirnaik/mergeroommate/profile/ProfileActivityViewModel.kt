package com.techmihirnaik.mergeroommate.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.techmihirnaik.mergeroommate.dao.ProfileDao

class ProfileActivityViewModel : ViewModel() {
    private val dao = ProfileDao()
    fun imageUpload(imageUri: Uri) {
        dao.imageUpload(imageUri)
    }
    fun getImage() {
        dao.getPostImage()
    }
    val profileData = dao.profileData
}