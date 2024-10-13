package com.trainerview.app.features.update_group_screen

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.trainerview.app.R
import com.trainerview.app.databinding.LiParticipantBinding

typealias ClickAction = (ParticipantListItem) -> Unit

class ParticipantAdapter : RecyclerView.Adapter<ParticipantViewHolder>() {

    private var items: List<ParticipantListItem> = emptyList()

    var onItemClickListener: ClickAction? = null
    var onItemLongClickListener: ClickAction? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiParticipantBinding.inflate(inflater, parent, false)
        return ParticipantViewHolder(
            binding = binding,
            onItemClickListener = onItemClickListener,
            onItemLongClickListener = onItemLongClickListener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(items[position],)
    }

    fun update(newGroups: List<ParticipantListItem>) {
        items = newGroups
        notifyDataSetChanged()
    }
}


class ParticipantViewHolder(
    private val binding: LiParticipantBinding,
    private val onItemClickListener: ClickAction?,
    private val onItemLongClickListener: ClickAction?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ParticipantListItem) {
        binding.title.text = item.name
        binding.root.setOnLongClickListener {
            onItemLongClickListener?.invoke(item)
            true
        }
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
        binding.root.background = when (item.isSelected) {
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