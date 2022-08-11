package com.techmihirnaik.mergeroommate.cab

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.databinding.ActivityCabBinding
import com.techmihirnaik.mergeroommate.placeSearch.PlaceAutocompleteActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
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
    private lateinit var carTypeSpinner: Spinner
    private lateinit var luggageSpinner: Spinner
    private val TO_PLACE_REQUEST_CODE = 100
    private val FROM_PLACE_REQUEST_CODE = 101
    private lateinit var calendar: Calendar
    private val TAG = "com.techmihirnaik.mergeroommate.cab.CabActivity"
    private val PLACE_AUTOCOMPLETE_CODE_TO = 101
    private val PLACE_AUTOCOMPLETE_CODE_FROM = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchBoxTo = binding.etSearchBoxTo
        searchBoxFrom = binding.etSearchBoxFrom
        dateText = binding.tvDateText
        timeText = binding.tvTimeText
        carTypeSpinner = binding.spinner1
        personSpinner = binding.spinner2
        luggageSpinner = binding.spinner3

        calendar = Calendar.getInstance()

        dateText.text = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        timeText.text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())

        searchBoxTo.setOnClickListener {
            val intent = Intent(this, PlaceAutocompleteActivity::class.java)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_CODE_TO)
        }

        searchBoxFrom.setOnClickListener {
            val intent = Intent(this, PlaceAutocompleteActivity::class.java)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_CODE_FROM)
        }

        dateText.setOnClickListener {
            dateDialogFunc()
        }

        timeText.setOnClickListener {
            timeDialogFunc()
        }

        setupSpinner()

        binding.bookBtn.setOnClickListener {
            bookFunc()
        }

    }

    private fun bookFunc() {

    }

    //Adapter function for spinners
    private fun setupSpinner() {

        //Adapter for person spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.car_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            carTypeSpinner.adapter = adapter
        }
        carTypeSpinner.onItemSelectedListener = carTypeSpinnerListener


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

    //Person spinner listener
    private val carTypeSpinnerListener: AdapterView.OnItemSelectedListener =
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
        var hourOfDay = p1
        val AM_PM = if (p1 < 12) {
            "AM"
        } else {
            hourOfDay = p1-12
            "PM"
        }
        val time = "$hourOfDay : $p2 $AM_PM"
        timeText.text = time

    }


    //Intent activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == PLACE_AUTOCOMPLETE_CODE_TO) {
                data?.getStringExtra("cityName")
                val address = data?.getStringExtra("address")
                address?.let { searchBoxTo.setText(address) }
            } else if (requestCode == PLACE_AUTOCOMPLETE_CODE_FROM) {
                data?.getStringExtra("cityName")
                val address = data?.getStringExtra("address")
                address?.let { searchBoxFrom.setText(address) }
            }
        }
    }


}