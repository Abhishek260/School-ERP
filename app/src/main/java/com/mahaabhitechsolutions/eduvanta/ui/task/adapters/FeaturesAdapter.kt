package com.mahaabhitechsolutions.eduvanta.ui.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahaabhitechsolutions.eduvanta.databinding.ItemFeaturesBinding
import com.mahaabhitechsolutions.eduvanta.ui.task.model.DataAnnual
import com.mahaabhitechsolutions.eduvanta.R


class FeaturesAdapter(private val features: List<DataAnnual>) :
    RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder>() {

    inner class FeatureViewHolder(val binding: ItemFeaturesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val binding = ItemFeaturesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val item = features[position]
        holder.binding.tvFeatureText.text = item.text ?: ""

        // icon and locked look
        if (item.isLocked=="True") {
            holder.binding.imageView.setImageResource(R.drawable.lock_svg)
            holder.binding.tvFeatureText.alpha = 0.5f
        } else {
            holder.binding.imageView.setImageResource(R.drawable.check_svg)
            holder.binding.tvFeatureText.alpha = 1f
        }
    }

    override fun getItemCount(): Int = features.size
}
