package com.example.busmap.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busmap.R
import com.example.busmap.core.domain.models.BusStop

class FavouriteRecycleView(
    private val stops : MutableList<BusStop>,
    private val onClickListener: OnClickListener,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<FavouriteRecycleView.FavouriteViewHolder>() {
    //Child class and interfaces
    class FavouriteViewHolder(view : View) : RecyclerView.ViewHolder(view) {}
    interface OnDeleteClickListener {
        fun onDelete(busStop: BusStop)
    }
    interface OnClickListener {
        fun onClick(busStop: BusStop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_recycle_item, parent, false)

        return FavouriteViewHolder(view)
    }

    override fun getItemCount() = stops.size

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        //Get stop
        val stop = stops[position]

        //Populate view
        val name = holder.itemView.findViewById<TextView>(R.id.saved_name)
        val address = holder.itemView.findViewById<TextView>(R.id.saved_address)
        val deleteButton = holder.itemView.findViewById<ImageButton>(R.id.saved_delete_button)

        Log.i("STOP", stop.toString())
        name.text = "${stop.code} ${stop.name}"
        address.text = "${stop.address} ${stop.street}"

        //Set on listener
        holder.itemView.setOnClickListener {
            onClickListener.onClick(stop)
        }

        deleteButton.setOnClickListener {
            onDeleteClickListener.onDelete(stop)
        }
    }

    fun update(stops : List<BusStop>) {
        this.stops.apply {
            clear()
            addAll(stops)
        }
        notifyDataSetChanged()
    }
}