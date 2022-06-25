package com.techmihirnaik.mergeroommate.placeSearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techmihirnaik.mergeroommate.R

class PlaceAutoCompleteAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<PlaceAutoCompleteViewHolder>() {

    private val placeList = ArrayList<Place>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAutoCompleteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_auto_complete_item_layout, parent, false)
        val viewHolder = PlaceAutoCompleteViewHolder(view)
        view.setOnClickListener {
            listener.itemClicked(placeList[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: PlaceAutoCompleteViewHolder, position: Int) {
        val place = placeList[position]
        holder.cityName.text = place.cityName
        holder.fullAddress.text = place.fullAddress
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    fun updatePlaces(newList: ArrayList<Place>) {
        placeList.clear()
        placeList.addAll(newList)

        notifyDataSetChanged()
    }
}

class PlaceAutoCompleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cityName: TextView = itemView.findViewById(R.id.tv_city_name)
    val fullAddress: TextView = itemView.findViewById(R.id.tv_full_address)
}

interface OnItemClickListener {
    fun itemClicked(place: Place)
}