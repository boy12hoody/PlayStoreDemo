package uz.boywonder.playstoredemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.boywonder.playstoredemo.databinding.ParentRecyclerviewItemsBinding
import uz.boywonder.playstoredemo.model.CategoryItem
import uz.boywonder.playstoredemo.util.MyDiffUtil

class ParentRecyclerViewAdapter :
    RecyclerView.Adapter<ParentRecyclerViewAdapter.ParentViewHolder>() {

    private var parentItemList = emptyList<CategoryItem>()

    inner class ParentViewHolder(private val binding: ParentRecyclerviewItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryItem: CategoryItem) {
            binding.apply {
                val childRvAdapter by lazy { ChildRecyclerViewAdapter() }

                picCategory.text = categoryItem.category
                childRecyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = childRvAdapter
                    childRvAdapter.setNewData(categoryItem.items)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ParentRecyclerviewItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val currentItem = parentItemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return parentItemList.size
    }

    fun setNewData(newData: List<CategoryItem>) {
        val diffUtil = MyDiffUtil(parentItemList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        parentItemList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}