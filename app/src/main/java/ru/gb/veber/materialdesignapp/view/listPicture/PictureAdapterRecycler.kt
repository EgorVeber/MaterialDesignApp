package ru.gb.veber.materialdesignapp.view.listPicture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.PictureItemTextBinding
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500

class PictureAdapterRecycler : RecyclerView.Adapter<PictureAdapterRecycler.BaseViewHolder>() {


    private var datelist: MutableList<Triple<PictureDTO, Boolean, Int>> = mutableListOf()
    fun setDate(newDate: MutableList<Triple<PictureDTO, Boolean, Int>>) {
        this.datelist = newDate
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return this.datelist[position].third
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            0 -> {
                TextViewHolder(PictureItemTextBinding.inflate(LayoutInflater.from(parent.context)))
            }
            1 -> {
//                val binding =
//                    ActivityRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
//                MarsViewHolder(binding.root)
                TextViewHolder(PictureItemTextBinding.inflate(LayoutInflater.from(parent.context)))

            }
            else -> {
//                val binding =
//                    ActivityRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
//                MarsViewHolder(binding.root)
                TextViewHolder(PictureItemTextBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(datelist[position])
    }

    override fun getItemCount(): Int {
        return datelist.size
    }

    inner class TextViewHolder(val binding: PictureItemTextBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(date: Triple<PictureDTO, Boolean, Int>) {
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
        }
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(date: Triple<PictureDTO, Boolean, Int>)
    }
}

