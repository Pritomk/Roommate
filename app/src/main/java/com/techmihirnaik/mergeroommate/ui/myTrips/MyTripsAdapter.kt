package com.techmihirnaik.mergeroommate.ui.myTrips

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.model.OrderItem
import java.text.SimpleDateFormat
import java.util.*

class MyTripsAdapter : RecyclerView.Adapter<MyTripsViewHolder>(), Filterable {

    private val tripsList: ArrayList<OrderItem> = ArrayList()
    private val allTripList: ArrayList<OrderItem> = ArrayList()
    private lateinit var context: Context
    private val TAG = "com.techmihirnaik.mergeroommate.ui.myTrips.MyTripsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTripsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotel_list_item, parent, false)
        val viewHolder = MyTripsViewHolder(view)

        context = parent.context

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyTripsViewHolder, position: Int) {
        val item = tripsList[position]
        if (item.category == "hostel") {
            holder.title.text = item.name
            holder.location.text = item.place
            holder.services.text = "Services :- ${item.services}"

            dateCounter(item.date, holder.dayLeft, holder.date)

            setTripImage(item.imageReference, context, holder.tripsPic)
        }
    }

    override fun getItemCount(): Int {
        return tripsList.size
    }



    fun updateTrips(newList: List<OrderItem>) {
        tripsList.clear()
        tripsList.addAll(newList)

        allTripList.clear()
        allTripList.addAll(newList)

        notifyDataSetChanged()
    }

    private fun setTripImage(child: String, context: Context, imageView: ImageView) {
        val storage = Firebase.storage
        val reference = storage.reference.child(child)
        reference.downloadUrl.addOnSuccessListener {
            Log.e("image_url_get", it.toString())
            Glide.with(context).load(it).into(imageView)
        }.addOnFailureListener { e ->
            Log.e("image_url_get", "Failed ${e.message} $child")
        }
    }

    private fun setDate(textView: TextView, dateVal: Long) {
        val date = Date(dateVal)
        val df2 = SimpleDateFormat("dd/MM/yy")
        val dateText: String = df2.format(date)
        textView.text = dateText
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun dateCounter(booking: Long, dayLeft: TextView, date: TextView) {
        val bookingDate = Date(booking)
        val currentDate = Date()

        val df1 = SimpleDateFormat("dd/MM/yy")
        val df2 = SimpleDateFormat("dd")

        val diff = bookingDate.time - currentDate.time
        val diffDate = Date(diff)

        val bookingDateText = df1.format(bookingDate)
        val diffDateText = df2.format(diffDate)

        dayLeft.text = "$diffDateText Days Left"
        date.text = "Checked In Date:- $bookingDateText"
    }


//    Run on backend thread
    override fun getFilter(): Filter {
        return filterMethod
    }

    private val  filterMethod = object : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filteredList : ArrayList<OrderItem> = ArrayList()
            if (charSequence!!.isEmpty()) {
                filteredList.addAll(tripsList)
            } else {
                for (item in allTripList) {
                    if (item.name.lowercase().contains(charSequence.toString().lowercase())) {
                        filteredList.add(item)
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
            tripsList.clear()
            val list = filterResults?.values as? Collection<*>

            list?.let{
                Log.d(TAG, "Executed")
                tripsList.addAll(it as Collection<OrderItem>)
            }


            notifyDataSetChanged()
        }

    }

}

class MyTripsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tripsPic: ImageView = itemView.findViewById(R.id.iv_tripsPic)
    val title: TextView = itemView.findViewById(R.id.tv_tripsItemTitle)
    val location: TextView = itemView.findViewById(R.id.tv_tripsItemLLocation)
    val date: TextView = itemView.findViewById(R.id.tv_tripsItemDate)
    val services: TextView = itemView.findViewById(R.id.tv_tripsItemServices)
    val dayLeft: TextView = itemView.findViewById(R.id.tv_tripsItemLLeft)
}