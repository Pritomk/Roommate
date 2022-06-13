package com.techmihirnaik.mergeroommate.schedule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.databinding.TravellersAddBinding

class AddTravellers:AppCompatActivity() {
    private lateinit var binding: TravellersAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.travellers_add)



        binding.BackHostel.setOnClickListener {
            val intent=Intent(this,Schedule::class.java)
            startActivity(intent)
        }



        //room count
        var roomCount=0
        binding.roomCountText.text=roomCount.toString()

        //increase room count
        binding.roomCountAdd.setOnClickListener {
            roomCount += 1
            binding.roomCountText.text=roomCount.toString()

        }

        //decrease room count
        binding.roomCountRemove.setOnClickListener {
            roomCount -= 1
            if(roomCount<0)
                roomCount=0
            binding.roomCountText.text=roomCount.toString()
        }


        //adult count
        var adultCount=0
        binding.adultCountText.text=adultCount.toString()

        //increase adult count
        binding.adultCountAdd.setOnClickListener {
            adultCount += 1
            binding.adultCountText.text=adultCount.toString()

        }

        //decrease adult count
        binding.adultCountRemove.setOnClickListener {
            adultCount -= 1
            if(adultCount<0)
                adultCount=0
            binding.adultCountText.text=adultCount.toString()
        }


        //children count
        var childrenCount=0
        binding.childrenCountText.text=childrenCount.toString()

        //increase children count
        binding.childrenCountAdd.setOnClickListener {
            childrenCount += 1
            binding.childrenCountText.text=childrenCount.toString()

        }

        //decrease children count
        binding.childrenCountRemove.setOnClickListener {
            childrenCount -= 1
            if(childrenCount<0)
                childrenCount=0
            binding.childrenCountText.text=childrenCount.toString()
        }




        //go to schedule page with data
        binding.doneRoomCount.setOnClickListener {

            val intent=Intent(this,Schedule::class.java)
            intent.putExtra("room",roomCount)
            intent.putExtra("adult",adultCount)
            intent.putExtra("children",childrenCount)
            startActivity(intent)
        }

    }

}