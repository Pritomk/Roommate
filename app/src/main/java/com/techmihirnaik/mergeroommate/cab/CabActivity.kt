package com.techmihirnaik.mergeroommate.cab

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.mapmyindia.sdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mapmyindia.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mmi.services.account.MapmyIndiaAccountManager
import com.mmi.services.api.autosuggest.model.ELocation
import com.mmi.services.api.autosuggest.model.SuggestedSearchAtlas
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.databinding.ActivityCabBinding
import java.text.DateFormat
import java.util.*

class CabActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityCabBinding
    private lateinit var searchBoxTo: EditText
    private lateinit var searchBoxFrom: EditText
    private lateinit var dateText: TextView
    private lateinit var timeText: TextView
    private lateinit var carTypeRG: RadioGroup
    private lateinit var personSpinner: Spinner
    private lateinit var luggageSpinner: Spinner
    private val TO_PLACE_REQUEST_CODE = 100
    private val FROM_PLACE_REQUEST_CODE = 101
    private lateinit var calendar: Calendar
    private val TAG = "com.techmihirnaik.mergeroommate.cab.CabActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchBoxTo = binding.etSearchBoxTo
        searchBoxFrom = binding.etSearchBoxFrom
        dateText = binding.tvDateText
        timeText = binding.tvTimeText
        carTypeRG = binding.rgCarType
        personSpinner = binding.spinner1
        luggageSpinner = binding.spinner2

        calendar = Calendar.getInstance()

        dateText.text = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        timeText.text = "${Calendar.HOUR}:${Calendar.MINUTE}"

        Places.initialize(this, getString(R.string.place_api_key))

        searchBoxTo.setOnClickListener {
            fromSearchBoxFunction(it)
        }

        searchBoxFrom.setOnClickListener {
            fromSearchBoxFunction(it)
        }

        dateText.setOnClickListener {
            dateDialogFunc()
        }

        timeText.setOnClickListener {
            timeDialogFunc()
        }

        setupSpinner()

    }

    //Adapter function for spinners
    private fun setupSpinner() {

        //Adapter for person spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.number_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            personSpinner.adapter = adapter
        }
        personSpinner.onItemSelectedListener = personSpinnerListener

        // Adapter for luggage spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.number_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            luggageSpinner.adapter = adapter
        }
        luggageSpinner.onItemSelectedListener = luggageSpinnerListener
    }


    //luggage spinner listener
    private val luggageSpinnerListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    //Person spinner listener
    private val personSpinnerListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }


    //Date picker function
    private fun dateDialogFunc() {
        val datePickerDialog = DatePicker()
        datePickerDialog.show(supportFragmentManager, "DATE PICK")
    }

    override fun onDateSet(p0: android.widget.DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(Calendar.YEAR, p1)
        calendar.set(Calendar.MONTH, p2)
        calendar.set(Calendar.DAY_OF_MONTH, p3)
        val selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        dateText.text = selectedDate
    }

    //Timer picker function

    private fun timeDialogFunc() {
        val timePickerDialog = TimePicker()
        timePickerDialog.show(supportFragmentManager, "TIME PICK")
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        calendar.set(Calendar.HOUR, p1)
        calendar.set(Calendar.MINUTE, p2)
        val selectedTime = "${p1}:${p2}"
        timeText.text = selectedTime
    }

    //place search box for to edittext
//    private fun toSearchBoxFunction() {
//        val placeOptions: PlaceOptions = PlaceOptions.builder()
//            .location(MapmyIndiaPlaceWidgetSetting.instance.location)
//            .filter(MapmyIndiaPlaceWidgetSetting.instance.filter)
//            .hint(MapmyIndiaPlaceWidgetSetting.instance.hint)
//            .saveHistory(MapmyIndiaPlaceWidgetSetting.instance.isEnableHistory)
//            .enableTextSearch(MapmyIndiaPlaceWidgetSetting.instance.isEnableTextSearch)
//            .pod(MapmyIndiaPlaceWidgetSetting.instance.pod)
//            .attributionHorizontalAlignment(MapmyIndiaPlaceWidgetSetting.instance.signatureVertical)
//            .attributionVerticalAlignment(MapmyIndiaPlaceWidgetSetting.instance.signatureHorizontal)
//            .logoSize(MapmyIndiaPlaceWidgetSetting.instance.logoSize)
//            .backgroundColor(resources.getColor(MapmyIndiaPlaceWidgetSetting.instance.backgroundColor))
//            .toolbarColor(resources.getColor(MapmyIndiaPlaceWidgetSetting.instance.toolbarColor))
//            .bridge(MapmyIndiaPlaceWidgetSetting.instance.isBridgeEnable)
//            .hyperLocal(MapmyIndiaPlaceWidgetSetting.instance.isHyperLocalEnable)
//            .build(PlaceOptions.MODE_CARDS)
//
//        val builder = PlaceAutocomplete.IntentBuilder()
//        if (!MapmyIndiaPlaceWidgetSetting.instance.isDefault) {
//            builder.placeOptions(placeOptions)
//        } else {
//            builder.placeOptions(PlaceOptions.builder().build(PlaceOptions.MODE_CARDS))
//        }
//        val placeAutocomplete = builder.build(this)
//        startActivityForResult(placeAutocomplete, TO_PLACE_REQUEST_CODE)
//
//    }

    // place search box for from edittext
    private fun fromSearchBoxFunction(view: View) {
        //Initialize place field list
        val fieldList = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)

        //Autocomplete intent
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fieldList
        ).build(this)


        //start intent with result code
        startActivityForResult(intent, FROM_PLACE_REQUEST_CODE)

    }

    private fun mapInitialize() {
        MapmyIndiaAccountManager.getInstance().restAPIKey = "87a22168-74dd-4620-91ff-5edb5193611c"
        MapmyIndiaAccountManager.getInstance().mapSDKKey = R.string.map_sdk_key.toString()
        MapmyIndiaAccountManager.getInstance().atlasClientId = R.string.client_id.toString()
        MapmyIndiaAccountManager.getInstance().atlasClientSecret =
            R.string.client_secret_key.toString()
    }


    //Intent activity result
    @SuppressLint("LogNotTimber")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == TO_PLACE_REQUEST_CODE) {
                val eLocation: ELocation? = PlaceAutocomplete.getPlace(data)
                if (eLocation != null) {
                    Log.d(
                        TAG,
                        "${eLocation.latitude?.toDouble()},${eLocation.longitude?.toDouble()}")
                    Toast.makeText(this, "${eLocation.latitude?.toDouble()},${eLocation.longitude?.toDouble()}", Toast.LENGTH_SHORT).show()
                } else {
                    val suggestedSearchAtlas: SuggestedSearchAtlas? =
                        PlaceAutocomplete.getSuggestedSearch(data)
                    if (suggestedSearchAtlas != null) {

                    }
                }
            } else if (requestCode == FROM_PLACE_REQUEST_CODE) {
                // Initialize place
                val place = Autocomplete.getPlaceFromIntent(data)
                //set Address in edittext
                searchBoxFrom.setText(place.address)
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status = data?.let { Autocomplete.getStatusFromIntent(it) }
            if (status != null) {
                Toast.makeText(this, status.statusMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }


}