package com.techmihirnaik.mergeroommate

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.firebase.auth.FirebaseAuth
import com.techmihirnaik.mergeroommate.databinding.ActivityMainBinding
import com.techmihirnaik.mergeroommate.location.GetAddressIntentService
import com.techmihirnaik.mergeroommate.ui.HomeFragment
import com.techmihirnaik.mergeroommate.ui.myTrips.MyTripsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigation: MeowBottomNavigation
    private lateinit var notificationImage: ImageView
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 2
    private var addressResultReceiver: LocationAddressResultReceiver? = null
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
            startActivity(Intent(this, NotificationActivity::class.java))
        }


//        //Address fetching
//        addressResultReceiver = LocationAddressResultReceiver(Handler())
//        //bottomNavigation=findViewById(R.id.bottomNavigation);
//        //bottomNavigation=findViewById(R.id.bottomNavigation);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                currentLocation = locationResult.getLocations().get(0)
//                getAddress()
//            }
//        }
//
//        currentAddTv = binding.customToolbar.currentAdd
//        currentAddTv!!.setOnClickListener {
////            startLocationUpdates()
//        }


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


    //Fetching address

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            val locationRequest = LocationRequest()
            locationRequest.fastestInterval = 2000
            locationRequest.fastestInterval = 1000
            locationRequest.priority = PRIORITY_HIGH_ACCURACY
//            fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(
                this@MainActivity, "Can't find current address, ",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val intent = Intent(this, GetAddressIntentService::class.java)
        intent.putExtra("add_receiver", addressResultReceiver)
        intent.putExtra("add_location", currentLocation)
        startService(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(
                    this,
                    "Location permission not granted, " + "restart the app if you want the feature",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    inner class LocationAddressResultReceiver(handler: Handler?) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying")
                getAddress()
            }
            if (resultCode == 1) {
                Toast.makeText(this@MainActivity, "Address not found, ", Toast.LENGTH_SHORT).show()
            }
            val currentAdd = resultData.getString("address_result")
            if (currentAdd != null) {
                showResults(currentAdd)
            }
        }
    }

    private fun showResults(currentAdd: String) {
        currentAddTv!!.text = currentAdd
    }

    override fun onResume() {
        super.onResume()
//        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
//        fusedLocationClient!!.removeLocationUpdates(locationCallback)
    }


}