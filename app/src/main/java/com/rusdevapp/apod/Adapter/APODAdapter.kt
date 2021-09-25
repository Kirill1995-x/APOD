package com.rusdevapp.apod.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rusdevapp.apod.Model.ModelNASA
import com.rusdevapp.apod.R

class APODAdapter(private val element: List<ModelNASA>): RecyclerView.Adapter<APODAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        fun bind(element: ModelNASA)
        {
            var tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
            var tvDate:TextView = itemView.findViewById(R.id.tvDate)
            tvTitle.text = element.title
            tvDate.text = element.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.data, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(element[position])
    }

    override fun getItemCount(): Int = element.size
}