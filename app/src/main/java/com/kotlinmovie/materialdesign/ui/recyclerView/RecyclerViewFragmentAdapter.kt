package com.kotlinmovie.materialdesign.ui.recyclerView

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.materialdesign.databinding.RecyclerItemEarthBinding
import com.kotlinmovie.materialdesign.databinding.RecyclerItemHeaderBinding
import com.kotlinmovie.materialdesign.databinding.RecyclerItemMarsBinding

class RecyclerViewFragmentAdapter(
    val onClickItemListener: OnClickItemListener,
    val onStartDragListener: OnStartDragListener
) : RecyclerView.Adapter<RecyclerViewFragmentAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    lateinit var listData: MutableList<Pair<RecyclerData, Boolean>>
    fun setData(listData: MutableList<Pair<RecyclerData, Boolean>>) {
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding =
                    RecyclerItemEarthBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                EarthViewHolder(binding.root)
            }
            TYPE_HEADER -> {
                val binding =
                    RecyclerItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                HeaderViewHolder(binding.root)
            }
            else -> {
                val binding =
                    RecyclerItemMarsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                MarsViewHolder(binding.root)

            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    override fun getItemCount() = listData.size

    fun addMars() {
        listData.add(generateMars())
        notifyItemInserted(listData.size - 1)
    }

    private fun generateMars() = Pair(RecyclerData("Марс", type = TYPE_MARS), false)

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<RecyclerData, Boolean>)
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<RecyclerData, Boolean>) {
            RecyclerItemMarsBinding.bind(itemView).apply {
                tvNameMars.text = data.first.name
                ivMars.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
                addItemImageView.setOnClickListener {
                    listData.add(layoutPosition, generateMars())
                    notifyItemInserted(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    listData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemUp.setOnClickListener {

                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)

                }
                moveItemDown.setOnClickListener {

                       listData.removeAt(layoutPosition).apply {
                           listData.add(layoutPosition + 1, this)
                       }
                       notifyItemMoved(layoutPosition, layoutPosition + 1)

                }
                marsDescriptionTextView.visibility = if (listData[layoutPosition].second)
                    View.VISIBLE else View.GONE
                itemView.setOnClickListener {
                    listData[layoutPosition] = listData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
                dragHandleImageView.setOnTouchListener { v, event ->

                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                        onStartDragListener.OnStartDrag(this@MarsViewHolder)
                    }
                    return@setOnTouchListener false
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listData.removeAt(fromPosition).apply {
            listData.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<RecyclerData, Boolean>) {
            RecyclerItemEarthBinding.bind(itemView).apply {
                tvNameEarth.text = data.first.name
                ivEarth.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<RecyclerData, Boolean>) {
            RecyclerItemHeaderBinding.bind(itemView).apply {
                tvNameHeader.text = data.first.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
            }
        }
    }
}

