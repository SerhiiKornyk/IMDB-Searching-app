package com.example.mvvmexample.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmexample.models.TVEntityUI
import com.example.mvvmexample.databinding.TvShowItemBinding

class TVShowsRecyclerViewAdapter(private var data: List<TVEntityUI>) :
    RecyclerView.Adapter<TVShowHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
        return TVShowHolder(
            TvShowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun updateData(data:List<TVEntityUI>) {
         this.data = data
        notifyDataSetChanged()
    }
}

class TVShowHolder(private val binding: TvShowItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(entity: TVEntityUI) {
          binding.tvEntity = entity
    }

}
