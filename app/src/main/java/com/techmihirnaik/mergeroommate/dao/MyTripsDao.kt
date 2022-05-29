package com.techmihirnaik.mergeroommate.dao

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.techmihirnaik.mergeroommate.model.OrderItem
import java.text.SimpleDateFormat
import java.util.*

class MyTripsDao {
    var orderedTrips: MutableLiveData<List<OrderItem>> = MutableLiveData()
    var historyTrips: MutableLiveData<List<OrderItem>> = MutableLiveData()
    private val firestore = FirebaseFirestore.getInstance()
    private val orderCollection = firestore.collection("order-list")

    private val TAG = "com.techmihirnaik.mergeroommate.dao.MyTripsDao"
    fun extractOrderedList() {
        orderCollection.document("uid")
            .collection("ordered")
            .addSnapshotListener { value, error ->
                val documents = value?.documents
                val orderItems = ArrayList<OrderItem>()
                if (documents != null) {
                    for (document in documents) {
                        Log.d(TAG,"$document")
                        val item = document.toObject(OrderItem::class.java)
                        if (item != null) {
                            orderItems.add(item)
                        }
                    }
                }
                orderedTrips.value = orderItems
            }
    }

    fun extractHistoryList() {
        orderCollection.document("uid")
            .collection("history")
            .addSnapshotListener { value, error ->
                val documents = value?.documents
                val orderItems = ArrayList<OrderItem>()
                if (documents != null) {
                    for (document in documents) {
                        Log.d(TAG,"$document")
                        val item = document.toObject(OrderItem::class.java)
                        if (item != null) {
                            orderItems.add(item)
                        }
                    }
                }
                historyTrips.value = orderItems
            }
    }

    fun insertItem(item: OrderItem, imageUri: Uri) {
        Log.d(TAG,"${Date(item.date)}")
        val imageReference = imageUpload(imageUri)
        item.imageReference = imageReference
        orderCollection.document("uid").collection("ordered").document().set(item)
            .addOnCompleteListener {
                Log.d(TAG, "Success")
            }
            .addOnFailureListener {
                Log.e(TAG, "$it")
            }
    }

    private fun imageUpload(imageUri: Uri): String {
        val storage = Firebase.storage
        val uid = Firebase.auth.uid
        val referenceChild = "order-list/uid/hostel/${getTimeDate()}"
        val reference = storage.reference.child(referenceChild)
        reference.putFile(imageUri)
            .addOnSuccessListener {
                Log.e("image_url", it.metadata?.path.toString())
            }
            .addOnFailureListener { e: Exception ->
                Log.e("image_url", e.message.toString() + " executed ")
            }
            .addOnProgressListener { taskSnapshot: UploadTask.TaskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                    .totalByteCount
            }
            .addOnCompleteListener {
                Log.e("image_url", it.result.toString())
            }
        return referenceChild
    }

    private fun getTimeDate(): String {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        return "$currentDate/$currentTime"
    }
}