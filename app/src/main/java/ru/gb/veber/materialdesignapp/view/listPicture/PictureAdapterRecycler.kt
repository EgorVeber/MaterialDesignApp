package ru.gb.veber.materialdesignapp.view.listPicture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.PictureItemImageBinding

import ru.gb.veber.materialdesignapp.databinding.PictureItemVideoBinding
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.utils.IMAGE_KEY
import ru.gb.veber.materialdesignapp.utils.VIDEO_KEY

class PictureAdapterRecycler : RecyclerView.Adapter<PictureAdapterRecycler.BaseViewHolder>() {

    private var pictureList: MutableList<Triple<PictureDTO, Boolean, Int>> = mutableListOf()

    fun setDate(newPictureList: MutableList<Triple<PictureDTO, Boolean, Int>>) {
        this.pictureList = newPictureList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return this.pictureList[position].third
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            IMAGE_KEY -> {
                ImageViewHolder(PictureItemImageBinding.inflate(LayoutInflater.from(parent.context)))
            }
            VIDEO_KEY -> {
                VideoViewHolder(PictureItemVideoBinding.inflate(LayoutInflater.from(parent.context)))
            }
            else -> {
                ImageViewHolder(PictureItemImageBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(pictureList[position])
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }


    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(date: Triple<PictureDTO, Boolean, Int>)
    }

    inner class ImageViewHolder(val binding: PictureItemImageBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(date: Triple<PictureDTO, Boolean, Int>) {

            var flag = date.second
            binding.explanation.visibility = if (date.second) View.VISIBLE else View.GONE

            with(binding) {
                title.text = date.first.title
                imageView.load(date.first.url) {
                    placeholder(R.drawable.loading1)
                    crossfade(CROSS_FADE_500)
                    error(R.drawable.nasa_api)
                }
                explanation.text = date.first.explanation
                datePicture.text = date.first.date
            }

            binding.description.setOnClickListener {
                flag = !flag
                pictureList[layoutPosition] = pictureList[layoutPosition].copy(second = flag)
                notifyItemChanged(layoutPosition)
            }

            binding.removeItemImageView.setOnClickListener {
                pictureList.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }


            binding.moveItemUp.setOnClickListener {
                if (layoutPosition > 0 && layoutPosition < pictureList.size) {
                    pictureList.removeAt(layoutPosition).apply {
                        pictureList.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
            }
            binding.moveItemDown.setOnClickListener {
                if (layoutPosition < pictureList.size - 1) {
                    pictureList.removeAt(layoutPosition).apply {
                        pictureList.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
        }
    }

    inner class VideoViewHolder(val binding: PictureItemVideoBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(date: Triple<PictureDTO, Boolean, Int>) {
            var flag = date.second
            binding.explanation.visibility = if (date.second) View.VISIBLE else View.GONE

            with(binding) {
                title.text = date.first.title
                imageView.load(R.drawable.nasa_api)
                explanation.text = date.first.explanation
                datePicture.text = date.first.date
            }

            binding.description.setOnClickListener {
                flag = !flag
                pictureList[layoutPosition] = pictureList[layoutPosition].copy(second = flag)
                notifyItemChanged(layoutPosition)
            }

            binding.removeItemImageView.setOnClickListener {
                pictureList.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }

            binding.moveItemUp.setOnClickListener {
                if (layoutPosition > 0 && layoutPosition < pictureList.size) {
                    pictureList.removeAt(layoutPosition).apply {
                        pictureList.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
            }
            binding.moveItemDown.setOnClickListener {
                if (layoutPosition < pictureList.size - 1) {
                    pictureList.removeAt(layoutPosition).apply {
                        pictureList.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
        }
    }
}

