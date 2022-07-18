package com.techmihirnaik.mergeroommate.cabChoice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import com.techmihirnaik.mergeroommate.R

class CabChoiceAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<CabChoiceViewHolder>() {

    private val carList = ArrayList<StorageReference>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabChoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_list_item_layout, parent, false)
        val viewHolder = CabChoiceViewHolder(view)
        context = parent.context
        view.setOnClickListener {
            listener.itemClicked(viewHolder.adapterPosition)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CabChoiceViewHolder, position: Int) {
        val carImage = carList[position]
        carImage.downloadUrl.addOnSuccessListener {
            Glide.with(context).load(it).into(holder.carImage)
        }.addOnFailureListener {
            Glide.with(context).load(R.drawable.ic_car_rental__1_).into(holder.carImage)
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    fun updatePlaces(newList: ArrayList<StorageReference>) {
        carList.clear()
        carList.addAll(newList)

        notifyDataSetChanged()
    }
}

class CabChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val carImage: ImageView = itemView.findViewById(R.id.iv_car)
}

interface OnItemClickListener {
    fun itemClicked(position: Int)
}