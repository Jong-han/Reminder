package com.jh.reminder.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.databinding.FragmentListItemBinding

class ListAdapter(private val onClickReminder: (Int)->Unit, private val onClickSwitch: (Int)->Unit): ListAdapter<ReminderEntity,ListViewHolder>(ListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentListItemBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(currentList[position], onClickReminder, onClickSwitch)
    }
}

class ListViewHolder(private val binding: FragmentListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReminderEntity, onClickReminder: (Int) -> Unit, onClickSwitch: (Int) -> Unit) {
        binding.run {
            data = item
            container.setOnClickListener {
                onClickReminder.invoke(adapterPosition)
            }
            swActive.setOnClickListener {
                onClickSwitch.invoke(adapterPosition)
            }
        }
    }
}

class ListDiffUtil: DiffUtil.ItemCallback<ReminderEntity>() {
    override fun areItemsTheSame(oldItem: ReminderEntity, newItem: ReminderEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReminderEntity, newItem: ReminderEntity): Boolean {
        return oldItem == newItem
    }
}