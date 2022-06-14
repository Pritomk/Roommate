package com.techmihirnaik.mergeroommate.schedule


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.squareup.timessquare.CalendarPickerView
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.databinding.DatePickerBinding
import com.techmihirnaik.mergeroommate.scheduleDatabase.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class DatePicker : AppCompatActivity() {
    lateinit var binding: DatePickerBinding
    var run by Delegates.notNull<Int>()
    lateinit var dateViewModel:DateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.date_picker)


        binding.BackHostel.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }


        val today = Date()
        val nextYear: Calendar = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 1)

        val datePicker: CalendarPickerView = binding.calendarView
        datePicker.init(today, nextYear.getTime())
            .inMode(CalendarPickerView.SelectionMode.RANGE)
            .withSelectedDate(today)

        var c = 0

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val currentYear = calendar[Calendar.YEAR]
        val currentDay = calendar[Calendar.DAY_OF_MONTH]
        calendar[Calendar.MONTH]

        val monthName = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov",
            "Dec"
        )
        val month = monthName[calendar[Calendar.MONTH]]
        val dateInWord=currentDay.toString() + " " + month

        binding.checkIn.text = currentDay.toString() + " " + month + " " + currentYear


        val cal = Calendar.getInstance(TimeZone.getDefault())

        val cYear = cal[Calendar.YEAR]
        val cMonth = cal[Calendar.MONTH] + 1
        val cDay = cal[Calendar.DAY_OF_MONTH]
        val cDate = "$cMonth/$cDay/$cYear"

        dateViewModel= ViewModelProvider(this@DatePicker).get(DateViewModel::class.java)


        //selecting date
        datePicker.setOnDateSelectedListener(object : CalendarPickerView.OnDateSelectedListener {
            override fun onDateSelected(date: java.util.Date?) {
                val calSelected = Calendar.getInstance()
                if (date != null) {
                    calSelected.time = date
                }
                val month_date = SimpleDateFormat("MMM")
                val month_name =
                    " " + calSelected[Calendar.DAY_OF_MONTH] + " " + month_date.format(calSelected.time)
                val date =
                    (calSelected[Calendar.MONTH]+1).toString() + "/" + calSelected[Calendar.DAY_OF_MONTH].toString() + "/" + calSelected[Calendar.YEAR].toString()

                if (c == 0) {
                    binding.checkOut.text = month_name
                    Log.d("dddd",date.toString())
                    dateViewModel.insert(Date(0,"0",date,"0",month_name))
                    c += 1

                } else if (c > 0) {
                    binding.checkIn.text = month_name
                    dateViewModel.insert(Date(0,date,"0",month_name,"0"))
                    c = 0

                }

            }
            override fun onDateUnselected(date: java.util.Date?) {
            }
        })

        val mDateFormat = SimpleDateFormat("MM/dd/yyyy")

        run=0

        //room database
        dateViewModel=ViewModelProvider(this).get(DateViewModel::class.java)
        dateViewModel.allDates.observe(
            this,
        ) { list ->

            /*   if(list.isNotEmpty()) {


                   Log.d("kkk", list.size.toString())
                   Log.d("kkk", list[0].cInDate)

                   if( list.size>1 && list[1].cOutDate != "0" && run==0) {
                      //if(list[0].cInDate==cDate && list[1].cOutDate!="0" && list.size>1) {

                       val mDate11 = mDateFormat.parse(cDate)
                       val mDate22 = mDateFormat.parse(list[0].cOutDate)

                       // Finding the absolute difference between
                       // the two dates.time (in milli seconds)
                       val mDifference = mDate11.time - mDate22.time
                       Log.d("kkk", "d1pase "+mDate11)
                       Log.d("kkk", "d1pase2 "+mDate22)
                       Log.d("kkk","size "+list.size.toString())


                       // Converting milli seconds to dates
                       val mDifferenceDates = mDifference / (24 * 60 * 60 * 1000)

                       // Converting the above integer to string
                       val mDayDifference = mDifferenceDates.toString()
                       Log.d("difff",mDayDifference)
                       binding.countNight.text = mDayDifference + " Nights"
                       run=1
                   }else if(list.size>1 && list[1].cInDate !="0" && list[0].cOutDate!="0")
                       {

                           Log.d("kkk", "cin" + list[1].cInDate)
                           val mDate111 = mDateFormat.parse(list[1].cInDate)
                           val mDate222 = mDateFormat.parse(list[0].cOutDate)
                           Log.d("kkk", "d1pase11 "+mDate111)
                           Log.d("kkk", "d1pase22 "+mDate222)
                           Log.d("kkk","size "+list.size.toString())

                           // Finding the absolute difference between
                           // the two dates.time (in milli seconds)
                           val mDifference =   mDate111.time-mDate222.time


                           // Converting milli seconds to dates
                           val mDifferenceDates = mDifference / (24 * 60 * 60 * 1000)

                           // Converting the above integer to string
                           val mDayDifference = mDifferenceDates.toString()

                           binding.countNight.text = mDayDifference + " Nights"
                       }
                //   }
               }
             */
            var r=0
            if(list.isNotEmpty() ) {

                if (list[0].cOutDate_2 !="0" && r==0) {
                    val cOut=list[0].cOutDate_2
                    binding.doneDate.setOnClickListener {
                        val intent = Intent(this, ScheduleActivity::class.java)
                        intent.putExtra("checkIn", dateInWord)
                        intent.putExtra("checkOut", cOut)
                        startActivity(intent)
                        dateViewModel.delete()
                        r=1
                    }
                }
                else if(list.size>1) {
                    val cIn=list[1].cInDate_1
                    val cOut=list[0].cOutDate_2
                    binding.doneDate.setOnClickListener {
                        val intent = Intent(this, ScheduleActivity::class.java)
                        intent.putExtra("checkIn", cIn)
                        intent.putExtra("checkOut", cOut)
                        startActivity(intent)
                    }
                }
            }
            // if(list.size>1) {
            dateViewModel.delete()
            //}

        }

    }



}