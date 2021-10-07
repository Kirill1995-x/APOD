package com.rusdevapp.apod.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rusdevapp.apod.Model.APODModel
import com.rusdevapp.apod.R

class APODViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)
{
    fun bind(model:APODModel)
    {
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        tvTitle.text=model.title
        tvDate.text=model.getConvertDate()
    }
}