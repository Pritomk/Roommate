package com.techmihirnaik.mergeroommate.cabChoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.techmihirnaik.mergeroommate.databinding.ActivityCabChoiceBinding

class CabChoiceActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityCabChoiceBinding
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCabChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CabChoiceAdapter(this)
        val recyclerView = binding.rvCabChoice
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        storageRef = FirebaseStorage.getInstance().reference.child("car_choice")
        storageRef.listAll().addOnSuccessListener { list ->
            val newList = ArrayList<StorageReference>()
            for (file in list.items) {
                newList.add(file)
            }
            adapter.updatePlaces(newList)
        }
    }

    override fun itemClicked(position: Int) {

    }
}