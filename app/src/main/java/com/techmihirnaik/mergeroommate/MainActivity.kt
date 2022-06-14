package com.techmihirnaik.mergeroommate

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.firebase.auth.FirebaseAuth
import com.techmihirnaik.mergeroommate.cab.CabActivity
import com.techmihirnaik.mergeroommate.databinding.ActivityMainBinding
import com.techmihirnaik.mergeroommate.ui.HomeFragment
import com.techmihirnaik.mergeroommate.ui.myTrips.MyTripsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigation: MeowBottomNavigation
    private lateinit var notificationImage: ImageView
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 2
    private var currentAddTv: TextView? = null
    private var currentLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    private val TAG = "com.techmihirnaik.mergeroommate.MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "${FirebaseAuth.getInstance().currentUser?.uid}")

//        setSupportActionBar(findViewById(R.id.ggtool))
        notificationImage = findViewById(R.id.iv_notification)

        bottomNavigation = binding.bottomNavigation
        bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_home))
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_explore))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_suitcase_1))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_settings))

        bottomNavigation.show(0, true)
        replaceFragment(HomeFragment.newInstance())
        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                0 -> {
                    replaceFragment(HomeFragment.newInstance())
                }
//                1 -> {
//                    replaceFragment(ExploreFragment.newInstance())
//                }
                2 -> {
                    replaceFragment(MyTripsFragment.newInstance())
                }
//                3 -> {
//                    replaceFragment(NotificationFragment.newInstance())
//                }
//                4 -> {
//                    replaceFragment(UserFragment.newInstance())
//                }
                else -> {
                    replaceFragment(HomeFragment.newInstance())
                }
            }

        }
        notificationImage.setOnClickListener {
//            startActivity(Intent(this, NotificationActivity::class.java))
            startActivity(Intent(this, CabActivity::class.java))
        }

    }

    private fun replaceFragment(newInstance: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, newInstance)
            .addToBackStack(Fragment::class.java.simpleName).commit()

    }

    fun addFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName).commit()
    }

}