package com.example.busmap.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busmap.R
import com.example.busmap.core.domain.models.BusNavigationDetails

class DirectionRecyclerAdapter(private val directionDetails : MutableList<BusNavigationDetails>)
    : RecyclerView.Adapter<DirectionRecyclerAdapter.DirectionViewHolder>() {
    class DirectionViewHolder(view : View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectionViewHolder {
        //Render view and return view holder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.direction_recycle_item, parent, false)

        return DirectionViewHolder(view)
    }

    override fun getItemCount() = directionDetails.size

    override fun onBindViewHolder(holder: DirectionViewHolder, position: Int) {
        //Get item
        val detail = directionDetails[position]

        //Find icons
        val icon = holder.itemView.findViewById<ImageView>(R.id.direction_item_icon)
        val desc = holder.itemView.findViewById<TextView>(R.id.direction_item_desc)
        val distance = holder.itemView.findViewById<TextView>(R.id.direction_item_distance)

        //Render
        icon.setImageResource(if (detail.isWalk) R.drawable.ic_directions_walk else R.drawable.ic_commute)
        desc.text = "${if (detail.isWalk) "Walk" else "Take the bus"} from ${detail.inName} to ${detail.offName}"
        distance.text = detail.distance.toString()
    }

    fun update(details : List<BusNavigationDetails>) {
        directionDetails.clear()
        directionDetails.addAll(details)
        notifyDataSetChanged()
    }
}