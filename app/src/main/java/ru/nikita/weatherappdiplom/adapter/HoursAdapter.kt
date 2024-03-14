package ru.nikita.weatherappdiplom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.nikita.weatherappdiplom.databinding.ItemWeekTempBinding
import ru.nikita.weatherappdiplom.dto.Hour
import ru.nikita.weatherappdiplom.utils.DateConverter

class HoursAdapter : ListAdapter<Hour, HourViewHolder>(HourDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val binding = ItemWeekTempBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)
    }
}


class HourViewHolder(
    private val binding: ItemWeekTempBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hour: Hour) {
        binding.dateItemWeek.text = DateConverter().convertDateWithHours(hour.time)
        binding.conditionItemWeek.text = hour.condition.text
        binding.tempItemWeek.text = hour.temp_c.toString()
        Glide.with(binding.imageItemWeek)
            .load("https:" + hour.condition.icon)
            .timeout(10_000)
            .into(binding.imageItemWeek)
    }
}


class HourDiffCallBack : DiffUtil.ItemCallback<Hour>() {
    override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
        return oldItem == newItem
    }
}