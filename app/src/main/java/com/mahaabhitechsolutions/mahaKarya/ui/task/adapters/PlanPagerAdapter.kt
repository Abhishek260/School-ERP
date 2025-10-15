package com.mahaabhitechsolutions.mahaKarya.ui.task.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mahaabhitechsolutions.mahaKarya.R
import com.mahaabhitechsolutions.mahaKarya.databinding.ItemPlanPageBinding
import com.mahaabhitechsolutions.mahaKarya.ui.task.adapters.FeaturesAdapter
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
            .into(object :CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable,transition: Transition<in Drawable>?) {
                    binding.planCard.background = resource
                }

                override fun onLoadCleared(placeholder:Drawable?) {
                    binding.planCard.background = placeholder
                }
            })

        binding.tvTag.text = plan.tagged_as ?: ""
        binding.tvPlanName.text = plan.plan_name ?: ""
        binding.tvPlanPrice.text = "₹${plan.cost_per_day}/day"
        binding.tvPlanDescription.text = plan.claims ?: ""

        plan.description?.data_annual?.let { features ->
            if (features.isNotEmpty()) {
                binding.rvFeatures.apply {
                    layoutManager = LinearLayoutManager(binding.root.context)
                    adapter = FeaturesAdapter(features)
                    visibility = View.VISIBLE
                }
            } else {
                binding.rvFeatures.visibility = View.GONE
            }
        } ?: run {
            binding.rvFeatures.visibility = View.GONE
        }

        // button text & background
        binding.buttonStart.text = plan.cta ?: binding.buttonStart.text
        Glide.with(binding.buttonStart.context)
            .load(plan.button_background)
            .placeholder(R.drawable.bg_dummy_new)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    binding.buttonStart.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    binding.buttonStart.background = placeholder
                }
            })

    }

    override fun getItemCount(): Int = plans.size
}
