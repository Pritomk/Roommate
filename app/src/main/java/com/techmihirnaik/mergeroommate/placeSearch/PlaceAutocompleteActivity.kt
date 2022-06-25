package com.techmihirnaik.mergeroommate.placeSearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.techmihirnaik.mergeroommate.databinding.ActivityPlaceAutocompleteBinding
import org.json.JSONObject

class PlaceAutocompleteActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityPlaceAutocompleteBinding
    private val TAG = "com.techmihirnaik.mergeroommate.placeSearch.PlaceAutoCompleteActivity"
    private var token: String? = null
    private lateinit var placeListRV: RecyclerView
    private lateinit var adapter: PlaceAutoCompleteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaceAutocompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferencesClass = getSharedPreferences("user_todo", MODE_PRIVATE)
        if (sharedPreferencesClass.contains("access_token")) {
            token = sharedPreferencesClass.getString("access_token", "")
        }

        placeListRV = binding.rvPlaceList

        adapter = PlaceAutoCompleteAdapter(this)
        placeListRV.adapter = adapter
        placeListRV.layoutManager = LinearLayoutManager(this)

        searchFunction()

    }

    private fun searchFunction() {
        binding.placeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    searchUpdateLocation(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    searchUpdateLocation(p0)
                }
                return false
            }

        })
    }

    private fun searchUpdateLocation(place: String) {

        val placeApi = "https://atlas.mapmyindia.com/api/places/geocode?address=$place&itemCount=5"

        val map = HashMap<String, String>()
        val jsonObjectRequest = object :
            JsonObjectRequest(Method.GET, placeApi, JSONObject(map as Map<*, *>?), { response ->
                val jsonArray = response.getJSONArray("copResults")

                if (jsonArray.length() == 0) {

                } else {
                    val placeList = ArrayList<Place>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject: JSONObject = jsonArray[i] as JSONObject
                        val cityName = jsonObject.getString("city")
                        val address = jsonObject.getString("formattedAddress")

                        placeList.add(Place(cityName, address))
                    }
                    adapter.updatePlaces(placeList)

                }

            }, { error ->
                if (error.message != null) {
                    error.printStackTrace()
                }
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["Content-Type"] = "application/json"
                header["Authorization"] = "$token"
                return header
            }
        }

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }

    override fun itemClicked(place: Place) {
        val resultIntent = Intent()
        resultIntent.putExtra("cityName", place.cityName)
        resultIntent.putExtra("address", place.fullAddress)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}