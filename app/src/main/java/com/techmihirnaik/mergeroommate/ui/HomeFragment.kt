package com.techmihirnaik.mergeroommate.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.techmihirnaik.mergeroommate.databinding.FragmentHomeBinding
import com.techmihirnaik.mergeroommate.schedule.Schedule


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
            val intent= Intent(context, Schedule::class.java)
            startActivity(intent)
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
