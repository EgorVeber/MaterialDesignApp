package ru.gb.veber.materialdesignapp.view.listPicture.recycler


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.PictureItemImageBinding
import ru.gb.veber.materialdesignapp.databinding.PictureItemVideoBinding
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.utils.IMAGE_KEY
import ru.gb.veber.materialdesignapp.utils.VIDEO_KEY
import ru.gb.veber.materialdesignapp.utils.findVideoId
import ru.gb.veber.materialdesignapp.view.listPicture.Change
import ru.gb.veber.materialdesignapp.view.listPicture.DiffCallback
import ru.gb.veber.materialdesignapp.view.listPicture.createCombinedPayload


class PictureAdapterRecycler(var listener: ScrollingToPositionListener) :
    RecyclerView.Adapter<PictureAdapterRecycler.BaseViewHolder>(), ItemTouchHelperAdapter {

    private var pictureList: MutableList<Triple<PictureDTO, Boolean, Int>> = mutableListOf()

    fun setDate(newPictureList: MutableList<Triple<PictureDTO, Boolean, Int>>) {
        var result = DiffUtil.calculateDiff(DiffCallback(pictureList, newPictureList))
        result.dispatchUpdatesTo(this)
        this.pictureList.clear()
        this.pictureList.addAll(newPictureList)
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

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val combine =
                createCombinedPayload(payloads as List<Change<Triple<PictureDTO, Boolean, Int>>>)

            if (combine.oldData.first.title != combine.newData.first.title) {

                when (getItemViewType(position)) {

                    IMAGE_KEY -> {
                        var binding = PictureItemImageBinding.bind(holder.itemView)
                        with(binding) {
                            title.text = combine.newData.first.title
                            imageView.load(combine.newData.first.url) {
                                placeholder(R.drawable.loading1)
                            }
                            explanation.text = combine.newData.first.explanation
                            datePicture.text = combine.newData.first.date
                        }
                    }

                    VIDEO_KEY -> {
                        var binding = PictureItemVideoBinding.bind(holder.itemView)
                        with(binding) {
                            title.text = combine.newData.first.title
                            explanation.text = combine.newData.first.explanation
                            datePicture.text = combine.newData.first.date
                        }
                    }

                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(pictureList[position])
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }


    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {
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
                listener.moveToPosition(adapterPosition)
            }
            binding.moveItemDown.setOnClickListener {
                if (layoutPosition < pictureList.size - 1) {
                    pictureList.removeAt(layoutPosition).apply {
                        pictureList.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
                listener.moveToPosition(adapterPosition)
            }
        }

        override fun onItemSelected() {
            //TODO надо бырать нормальный цвет
//            val typedValue = TypedValue()
//            App.appInstance?.theme?.resolveAttribute(android.R.attr.colorPrimary,typedValue,true)
//            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, typedValue.resourceId))
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.b700))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class VideoViewHolder(val binding: PictureItemVideoBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(date: Triple<PictureDTO, Boolean, Int>) {
            var flag = date.second
            binding.explanation.visibility = if (date.second) View.VISIBLE else View.GONE

            with(binding) {
                title.text = date.first.title
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

            binding.youtubePlayer.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(findVideoId(date.first.url), 0f)
                }
            })
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.b700))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        pictureList.removeAt(fromPosition).apply {
            pictureList.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        pictureList.removeAt(position)
        notifyItemRemoved(position)
    }
}
