package com.mahaabhitechsolutions.mahaKarya.ui.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahaabhitechsolutions.mahaKarya.R
import com.mahaabhitechsolutions.mahaKarya.databinding.ItemFeaturesBinding
import com.mahaabhitechsolutions.mahaKarya.ui.task.model.DataAnnual

class FeaturesAdapter(private val features: List<DataAnnual>) :
    RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder>() {

    inner class FeatureViewHolder(val binding: ItemFeaturesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val binding =
            ItemFeaturesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeatureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val item = features[position]
        val binding = holder.binding

        // Text
        binding.tvFeatureText.text = item.text

        // Icon
        val icon = if (item.isLocked=="True") R.drawable.lock_svg else R.drawable.check_svg
        Glide.with(binding.root.context)
            .load(icon)
            .into(binding.root.findViewById(R.id.tvFeatureText))
    }

    override fun getItemCount(): Int = features.size
}
