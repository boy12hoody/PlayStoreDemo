package uz.boywonder.playstoredemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.ChildRecyclerviewItemsBinding
import uz.boywonder.playstoredemo.model.ImageItem
import uz.boywonder.playstoredemo.util.MyDiffUtil

class ChildRecyclerViewAdapter : RecyclerView.Adapter<ChildRecyclerViewAdapter.ChildViewHolder>() {

    private var childImageList = emptyList<ImageItem>()

    inner class ChildViewHolder(private val binding: ChildRecyclerviewItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageItem: ImageItem) {
            binding.apply {
                randomImageView.load(imageItem.downloadUrl) {
                    crossfade(600)
                    error(R.drawable.ic_broken_image)
                }
                randomImageName.text = imageItem.author
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ChildRecyclerviewItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val currentItem = childImageList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return childImageList.size
    }

    fun setNewData(newData: List<ImageItem>) {
        val diffUtil = MyDiffUtil(childImageList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        childImageList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}