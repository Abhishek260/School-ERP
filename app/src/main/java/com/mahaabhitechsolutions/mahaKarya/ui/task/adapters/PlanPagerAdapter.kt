package com.mahaabhitechsolutions.mahaKarya.ui.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahaabhitechsolutions.mahaKarya.R
import com.mahaabhitechsolutions.mahaKarya.databinding.ItemPlanPageBinding
import com.mahaabhitechsolutions.mahaKarya.ui.task.model.Data

class PlanPagerAdapter(private val plans: List<Data>) :
    RecyclerView.Adapter<PlanPagerAdapter.PlanViewHolder>() {

    inner class PlanViewHolder(val binding: ItemPlanPageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding =
            ItemPlanPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val plan = plans[position]
        val binding = holder.binding

        // Plan Image
        Glide.with(binding.ivPlanImage.context)
            .load(plan.banner)
            .placeholder(R.drawable.image)
            .into(binding.ivPlanImage)

        // Tag
        binding.tvTag.text = plan.tagged_as

        // Plan name
        binding.tvPlanName.text = plan.plan_name

        // Plan price per day
        binding.tvPlanPrice.text = "₹${plan.cost_per_day}/day"

        // Description / Claims
        binding.tvPlanDescription.text = plan.claims

        Glide.with(binding.planCard.context)
            .load(plan.button_background)
            .placeholder(R.drawable.bg_dummy_new)
            .into(object : com.bumptech.glide.request.target.CustomTarget<android.graphics.drawable.Drawable>() {
                override fun onResourceReady(
                    resource: android.graphics.drawable.Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in android.graphics.drawable.Drawable>?
                ) {
                    binding.planCard.background = resource
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                    binding.planCard.background = placeholder
                }
            })

        // TODO: set RecyclerView for features if needed
        // binding.rvFeatures.adapter = FeaturesAdapter(plan.description.data_annual)
    }

    override fun getItemCount(): Int = plans.size
}
