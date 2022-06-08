package com.techmihirnaik.mergeroommate.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.authentication.InitialActivity
import com.techmihirnaik.mergeroommate.databinding.FragmentProfileBinding
import com.techmihirnaik.mergeroommate.profile.ProfileActivity
import com.techmihirnaik.mergeroommate.ui.myTrips.MyTripsFragment
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {


    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileIV: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var emailTV: TextView
    private lateinit var numberTV: TextView
    private lateinit var logoutBtn: ImageView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private val TAG = "com.techmihirnaik.mergeroommate.ui.profile.ProfileFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileIV = binding.profileLayout.profileImage
        profileName = binding.profileLayout.profileName
        emailTV = binding.profileLayout.profileEmail
        numberTV = binding.profileLayout.profileNumber
        logoutBtn = binding.logoutBtn

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!
        Log.d(TAG,"${user.email} ${user.phoneNumber} ${user.displayName}")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireActivity()).load(user.photoUrl).into(profileIV)
        profileName.text = user.displayName
        emailTV.text = user.email
//        numberTV.text = user.phoneNumber

        logoutBtn.setOnClickListener {
            Glide.with(this).load(R.drawable.ic_baseline_power_settings_new_24).into(logoutBtn)
            firebaseAuth.signOut()
            startActivity(Intent(requireActivity(), InitialActivity::class.java))
        }

        binding.profileLayout.editBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }

    }

}