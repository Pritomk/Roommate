package com.techmihirnaik.mergeroommate.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.techmihirnaik.mergeroommate.cab.CabActivity
import com.techmihirnaik.mergeroommate.databinding.FragmentHomeBinding
import com.techmihirnaik.mergeroommate.rentalCar.RentalCarActivity
import com.techmihirnaik.mergeroommate.schedule.ScheduleActivity


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(
            inflater, com.techmihirnaik.mergeroommate.R.layout.fragment_home , container, false)


         binding.hotels.setOnClickListener {
            val intent= Intent(context, ScheduleActivity::class.java)
            startActivity(intent)
        }

        binding.ivCabBtn.setOnClickListener {
            val intent = Intent(requireContext(), CabActivity::class.java)
            startActivity(intent)
        }

        binding.ivLaundry.setOnClickListener {
            startActivity(Intent(requireContext(), RentalCarActivity::class.java))
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }


}
