package com.example.lab4_skill

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SkillProgressListAdapter internal constructor(
    context: Context,
    private val onItemClickListener: OnItemClickListener
) :
    ListAdapter<Progress, SkillProgressListAdapter.ProgressViewHolder>(ProgressComparator()) {

    interface OnItemClickListener {
        fun onItemClick(progress: Progress)
        fun onDeleteClick(progress: Progress)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val levelTextView: TextView = itemView.findViewById(R.id.levelTextView)
        val notesTextView: TextView = itemView.findViewById(R.id.notesTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val itemView = inflater.inflate(R.layout.progress_list_item, parent, false)
        return ProgressViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val current = getItem(position)
        holder.levelTextView.text = "Level: ${current.level}"
        holder.notesTextView.text = current.notes ?: "No notes"

        val date = Date(current.date)
        holder.dateTextView.text = dateFormat.format(date)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(current)
        }

        holder.deleteButton.setOnClickListener {
            onItemClickListener.onDeleteClick(current)
        }
    }

    class ProgressComparator : DiffUtil.ItemCallback<Progress>() {
        override fun areItemsTheSame(oldItem: Progress, newItem: Progress): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Progress, newItem: Progress): Boolean {
            return oldItem.level == newItem.level &&
                    oldItem.notes == newItem.notes &&
                    oldItem.date == newItem.date
        }
    }
}