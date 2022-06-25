package com.techmihirnaik.mergeroommate

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.firebase.auth.FirebaseAuth
import com.techmihirnaik.mergeroommate.cab.CabActivity
import com.techmihirnaik.mergeroommate.databinding.ActivityMainBinding
import com.techmihirnaik.mergeroommate.ui.HomeFragment
import com.techmihirnaik.mergeroommate.ui.myTrips.MyTripsFragment
import com.techmihirnaik.mergeroommate.utilServices.SharedPreferencesClass
import org.json.JSONException
import org.json.JSONObject

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
    private lateinit var sharedPreferencesClass: SharedPreferencesClass


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

        sharedPreferencesClass = SharedPreferencesClass(this)

        getMapMyIndiaToken()

    }

    private fun getMapMyIndiaToken() {
        val clientId = R.string.client_id.toString()
        val clientSecret = R.string.client_secret_key.toString()
        val tokenUrl = "https://outpost.mapmyindia.com/api/security/oauth/token?grant_type=client_credentials&client_id=33OkryzDZsIwRExUeQxLJzstMd2LGn72_SL8liSHmCrcSUK-7E3FM2zcn_VYoFeX6latOqr751dsqe8lneteEeBGXiT_yQ_C&client_secret=lrFxI-iSEg9wc1ZqXBSkhx96xnD8C-vYEV-a09n2qliqpj35ce5Lfs2jLGYb9vojQ_U00jV3lJo5rPYQorx8QoIe6KOJvOtme0kv_kPanU4="

        val map = HashMap<String, String>()

        val jsonObjectRequest = object : JsonObjectRequest(Method.POST, tokenUrl, JSONObject(map as Map<*, *>?), { response ->
            try {
                val token = response.getString("access_token")
                Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
                sharedPreferencesClass.setValueString("access_token", token)
            } catch (e: JSONException) {
                e.stackTrace
            }
        },{
            Toast.makeText(this, "Authentication error", Toast.LENGTH_SHORT).show()
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["Content-Type"] = "application/json"
                return header;
            }
        }

        val socketTime = 3000
        val policy = DefaultRetryPolicy(socketTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        jsonObjectRequest.retryPolicy = policy

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
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

    override fun onStart() {
        super.onStart()
        val sharedPreferencesClass = getSharedPreferences("user_todo", MODE_PRIVATE)
        if (!sharedPreferencesClass.contains("access_token")) {
            getMapMyIndiaToken()
        }

    }

}