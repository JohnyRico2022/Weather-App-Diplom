package ru.nikita.weatherappdiplom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.nikita.weatherappdiplom.databinding.ItemWeekTempBinding
import ru.nikita.weatherappdiplom.dto.Forecastday
import ru.nikita.weatherappdiplom.utils.DateConverter

interface OnInteractionListener {
    fun onItemClicked(itemDay: Forecastday){}
}

class DaysAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Forecastday, WeatherViewHolder>(WeekDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
       val binding = ItemWeekTempBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast)
    }
}

class WeatherViewHolder(
  private val  binding: ItemWeekTempBinding,
  private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: Forecastday) {
        binding.tempItemWeek.text = ("${forecast.day.avgtemp_c} °C")
        binding.dateItemWeek.text = DateConverter().convertDate(forecast.date)
        binding.conditionItemWeek.text = forecast.day.condition.text
        Glide.with(binding.imageItemWeek)
            .load("https:" + forecast.day.condition.icon)
            .timeout(10_000)
            .into(binding.imageItemWeek)
        itemListener(forecast)
    }

    private fun itemListener(forecast: Forecastday) {
        itemView.setOnClickListener {
            onInteractionListener.onItemClicked(forecast)
        }
    }

}

class WeekDiffCallback : DiffUtil.ItemCallback<Forecastday>() {
    override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
        return oldItem == newItem
    }

}
