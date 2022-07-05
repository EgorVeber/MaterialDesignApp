package ru.gb.veber.materialdesignapp.view.listPicture

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.PictureDayLayoutBinding
import ru.gb.veber.materialdesignapp.databinding.PictureItemTextBinding
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500

class PictureAdapterRecycler : RecyclerView.Adapter<PictureAdapterRecycler.HolderPicture>() {

    private var listPicture: List<PictureDTO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HolderPicture(
        PictureItemTextBinding.inflate(LayoutInflater.from(parent.context)).root
    )

    override fun onBindViewHolder(holder: HolderPicture, position: Int) =
        holder.bind(listPicture[position])

    override fun getItemCount() = listPicture.size

    fun setList(hoursData: List<PictureDTO>) {
        this.listPicture = hoursData
        notifyDataSetChanged()
    }

    inner class HolderPicture(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pictureDTO: PictureDTO) {
            with(PictureItemTextBinding.bind(itemView))
            {
                title.text = pictureDTO.title
                imageView.load(pictureDTO.url) {
                    placeholder(R.drawable.loading1)
                    crossfade(CROSS_FADE_500)
                    error(R.drawable.nasa_api)
                }
                explanation.text = pictureDTO.explanation
                datePicture.text = pictureDTO.date
            }
        }
    }
}

