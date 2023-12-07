package com.ispace.mycar.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ispace.mycar.R
import com.ispace.mycar.model.CarData

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var carList = emptyList<CarData>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = carList[position]
        holder.itemView.findViewById<TextView>(R.id.row_model).text = currentItem.carModel
        holder.itemView.findViewById<TextView>(R.id.row_plates).text = currentItem.carPlates
        holder.itemView.findViewById<TextView>(R.id.row_make).text = currentItem.carMake
        holder.itemView.findViewById<TextView>(R.id.row_date).text = currentItem.datePurchased
        holder.itemView.findViewById<TextView>(R.id.row_owner).text = currentItem.carOwner
        //holder.itemView.findViewById<TextView>(R.id.row_fuel).text = currentItem.carTare

        holder.itemView.findViewById<ViewGroup>(R.id.rowLayout).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToCarDetailsFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }




    }

    fun setData(car: List<CarData>) {
        this.carList = car
        notifyDataSetChanged()
    }
}