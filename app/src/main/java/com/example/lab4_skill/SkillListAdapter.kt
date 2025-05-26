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

class SkillListAdapter internal constructor(
    context: Context,
    private val onItemClickListener: OnItemClickListener
) :
    ListAdapter<Skill, SkillListAdapter.SkillViewHolder>(SkillsComparator()) {

    interface OnItemClickListener {
        fun onItemClick(skillId: Int)
        fun onDeleteClick(skill: Skill)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    inner class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skillItemView: TextView = itemView.findViewById(R.id.textView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        val lastEditedTextView: TextView = itemView.findViewById(R.id.lastEditedTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val itemView: View = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return SkillViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val current: Skill = getItem(position)
        holder.skillItemView.text = current.name
        holder.descriptionTextView.text = current.description ?: "Description is missing"

        val lastEdited = current.lastEdited?.let { Date(it) }
        holder.lastEditedTextView.text = lastEdited?.let { dateFormat.format(it) } ?: "Date is missing"

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(current.id)
        }

        holder.deleteButton.setOnClickListener {
            onItemClickListener.onDeleteClick(current)
        }
    }

    class SkillsComparator : DiffUtil.ItemCallback<Skill>() {
        override fun areItemsTheSame(oldItem: Skill, newItem: Skill): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Skill, newItem: Skill): Boolean {
            return oldItem.name == newItem.name && oldItem.description == newItem.description && oldItem.lastEdited == newItem.lastEdited
        }
    }
}