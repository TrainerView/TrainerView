package com.trainerview.app.presentation.group_list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.trainerview.app.R
import com.trainerview.app.data.db.model.GroupDb
import com.trainerview.app.databinding.LiGroupBinding

class GroupAdapter(
) : RecyclerView.Adapter<GroupViewHolder>() {

    private var items: List<GroupListItem> = emptyList()

    var onItemLongClickListener: ((GroupListItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiGroupBinding.inflate(inflater, parent, false)
        return GroupViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(items[position], onItemLongClickListener!!)
    }

    fun update(newGroups: List<GroupListItem>) {
        items = newGroups
        notifyDataSetChanged()
    }
}

class GroupViewHolder(
    private val binding: LiGroupBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(group: GroupListItem, onItemLongClickListener: (GroupListItem) -> Unit) {
        binding.liGroupTitle.text = group.name
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

class GroupListItem(
    val id: Long,
    val name: String,
    val isSelected: Boolean = false
)