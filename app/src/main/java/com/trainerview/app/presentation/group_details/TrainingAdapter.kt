package com.trainerview.app.presentation.group_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trainerview.app.databinding.LiGroupBinding
import com.trainerview.app.databinding.LiTrainingBinding
import com.trainerview.app.presentation.group_list.GroupListItem
import com.trainerview.app.presentation.group_list.GroupViewHolder

class TrainingAdapter : RecyclerView.Adapter<TrainingViewHolder>() {

    private var items: List<TrainingListItem> = emptyList()

    var onItemClickListener: ((TrainingListItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiTrainingBinding.inflate(inflater, parent, false)
        return TrainingViewHolder(
            binding = binding,
            onItemClickListener = onItemClickListener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(newItems: List<TrainingListItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}

class TrainingViewHolder(
    private val binding: LiTrainingBinding,
    private val onItemClickListener: ((TrainingListItem) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(training: TrainingListItem) {
        binding.liTrainingDate.text = training.date
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(training)
        }
    }
}

class TrainingListItem(
    val id: Long,
    val date: String
)