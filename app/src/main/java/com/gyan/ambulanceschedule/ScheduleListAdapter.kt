package com.gyan.ambulanceschedule

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gyan.ambulanceschedule.data.Schedule
import com.gyan.ambulanceschedule.data.getFormattedSchedule
import com.gyan.ambulanceschedule.databinding.ScheduleListItemBinding

class ScheduleListAdapter (private val onScheduleClicked: (Schedule) -> Unit) :
    ListAdapter<Schedule, ScheduleListAdapter.ScheduleViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            ScheduleListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onScheduleClicked(current)
        }
        holder.bind(current)
    }

    class ScheduleViewHolder(private var binding: ScheduleListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.apply {
                name.text = schedule.name
                phone.text = schedule.phone
            }
            binding.schedule.text = schedule.getFormattedSchedule()
        }
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }
}

