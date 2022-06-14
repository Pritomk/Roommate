package com.techmihirnaik.mergeroommate.schedule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.databinding.SearchHotelsBinding

class SearchHotels : AppCompatActivity() {
    private lateinit var binding: SearchHotelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.search_hotels)




        binding.backHostel.setOnClickListener {
            val intent= Intent(this,ScheduleActivity::class.java)
            startActivity(intent)
        }

    }
}