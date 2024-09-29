package com.trainerview.app.presentation.update_group

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.trainerview.app.R
import com.trainerview.app.databinding.LiParticipantBinding
import com.trainerview.app.presentation.group_list.GroupListItem

class ParticipantAdapter : RecyclerView.Adapter<ParticipantViewHolder>() {

    private var items: List<ParticipantListItem> = emptyList()

    var onItemLongClickListener: ((ParticipantListItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiParticipantBinding.inflate(inflater, parent, false)
        return ParticipantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(items[position], onItemLongClickListener!!)
    }

    fun update(newGroups: List<ParticipantListItem>) {
        items = newGroups
        notifyDataSetChanged()
    }
}


class ParticipantViewHolder(
    private val binding: LiParticipantBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(group: ParticipantListItem, onItemLongClickListener: (ParticipantListItem) -> Unit) {
        binding.liParticipantTitle.text = group.name
        binding.root.setOnLongClickListener {
            onItemLongClickListener.invoke(group)
            true
        }
        binding.root.background = when (group.isSelected) {
            true -> ColorDrawable(ContextCompat.getColor(binding.root.context, R.color.grey))
            false -> ColorDrawable(ContextCompat.getColor(binding.root.context, R.color.white))
        }
    }
}

class ParticipantListItem(
    val id: Long,
    val name: String,
    val isSelected: Boolean = false
)