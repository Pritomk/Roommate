package com.techmihirnaik.mergeroommate.rentalCar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.libraries.places.api.Places
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.cab.DatePicker
import com.techmihirnaik.mergeroommate.databinding.ActivityRentalCarBinding
import com.techmihirnaik.mergeroommate.placeSearch.PlaceAutocompleteActivity
import java.text.DateFormat
import java.util.*

class RentalCarActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityRentalCarBinding
    private lateinit var searchBox: EditText
    private lateinit var dateText: TextView
    private lateinit var timeText: TextView
    private lateinit var carTypeRG: RadioGroup
    private lateinit var personSpinner: Spinner
    private val TO_PLACE_REQUEST_CODE = 100
    private val FROM_PLACE_REQUEST_CODE = 101
    private lateinit var calendar: Calendar
    private val TAG = "com.techmihirnaik.mergeroommate.cab.CabActivity"
    private val PLACE_AUTOCOMPLETE_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRentalCarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        searchBox = binding.etSearchBoxPlace
        dateText = binding.tvDateText
        timeText = binding.tvTimeText
        carTypeRG = binding.rgCarType
        personSpinner = binding.spinner1

        calendar = Calendar.getInstance()

        dateText.text = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        timeText.text = "${Calendar.HOUR}:${Calendar.MINUTE}"

        Places.initialize(this, getString(R.string.place_api_key))

        searchBox.setOnClickListener {
            val intent = Intent(this, PlaceAutocompleteActivity::class.java)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_CODE)
        }

        dateText.setOnClickListener {
            dateDialogFunc()
        }

        timeText.setOnClickListener {
            timeDialogFunc()
        }

        setupSpinner()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == PLACE_AUTOCOMPLETE_CODE) {
                data?.getStringExtra("cityName")
                val address = data?.getStringExtra("address")
                Toast.makeText(this, address, Toast.LENGTH_SHORT).show()
                address?.let { searchBox.setText(address) }
            }
        }
    }

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
        val timePickerDialog = com.techmihirnaik.mergeroommate.cab.TimePicker()
        timePickerDialog.show(supportFragmentManager, "TIME PICK")
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        calendar.set(Calendar.HOUR, p1)
        calendar.set(Calendar.MINUTE, p2)
        val selectedTime = "${p1}:${p2}"
        timeText.text = selectedTime
    }

}