package com.techmihirnaik.mergeroommate.schedule


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.databinding.FragmentHomeBinding
import com.techmihirnaik.mergeroommate.databinding.ScheduleBinding
import com.techmihirnaik.mergeroommate.ui.HomeFragment
import java.util.*

class Schedule : AppCompatActivity() {
    private lateinit var binding: ScheduleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.schedule)


        //go back home
        binding.BackHome.setOnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }

            //go to search page
            binding.location.setOnClickListener {
                val intent = Intent(this, SearchHotels::class.java)
                startActivity(intent)
            }
            //go to addtravellers
            binding.roomLayout.setOnClickListener {
                val intent = Intent(this, AddTravellers::class.java)
                startActivity(intent)
            }

            binding.international.setOnClickListener {
                binding.international.setBackgroundResource(R.drawable.select_india_shape)
                binding.india.setBackgroundResource(R.drawable.unselect_india_shape)
                binding.internationalText.setTextColor(Color.WHITE)
                binding.indiaText.setTextColor(Color.BLACK)
            }
            binding.india.setOnClickListener {
                binding.india.setBackgroundResource(R.drawable.select_india_shape)
                binding.international.setBackgroundResource(R.drawable.unselect_india_shape)
                binding.indiaText.setTextColor(Color.WHITE)
                binding.internationalText.setTextColor(Color.BLACK)
            }


            //add count of room,adult,children from addTravellers
            val intent = intent
            val room = intent.getIntExtra("room", 0)
            binding.room.text = room.toString() + " Room"

            val adult = intent.getIntExtra("adult", 0)
            binding.adultsCount.text = adult.toString() + " Adult"

            val children = intent.getIntExtra("children", 0)
            binding.childrenCount.text = children.toString() + " Children"


            val calendar = Calendar.getInstance(TimeZone.getDefault())
            val currentYear = calendar[Calendar.YEAR]
            val currentDay = calendar[Calendar.DAY_OF_MONTH]
            calendar[Calendar.MONTH]

            val monthName = arrayOf(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov",
                "Dec"
            )
            val month = monthName[calendar[Calendar.MONTH]]

            binding.checkIn.text = currentDay.toString() + " " + month
            binding.checkOut.text = " - " + (currentDay + 1).toString() + " " + month
            val CheckINdate = intent.getStringExtra("checkIn")
            if (CheckINdate != null) {
                binding.checkIn.text = CheckINdate
            }
            val CheckOUTdate = intent.getStringExtra("checkOut")
            if (CheckOUTdate != null) {
                binding.checkOut.text = "-" + CheckOUTdate
            }


            //go to datePicker page
            binding.dateLayout.setOnClickListener {
                val intent = Intent(this, DatePicker::class.java)
                startActivity(intent)
            }

        }

}

