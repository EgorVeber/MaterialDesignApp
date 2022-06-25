package ru.gb.veber.materialdesignapp.view.planets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500

class PictureAdapterR : RecyclerView.Adapter<HolderPicture>() {

    private var hoursData: List<PictureDTO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HolderPicture(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_picture, parent, false) as View
    )

    override fun onBindViewHolder(holder: HolderPicture, position: Int) =
        holder.bind(hoursData[position])

    override fun getItemCount() = hoursData.size

    fun setList(hoursData: List<PictureDTO>) {
        this.hoursData = hoursData
        notifyDataSetChanged()
    }

}

class HolderPicture(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(pictureDTO: PictureDTO) {
        with(itemView)
        {
            //TODO проверка на видео работает но перетирается ссылка надо будет спросить как исправить
            findViewById<TextView>(R.id.titleR).text = pictureDTO.title
            findViewById<AppCompatImageView>(R.id.image_viewR).load(pictureDTO.url) {
                placeholder(R.drawable.loading1)
                crossfade(CROSS_FADE_500)
                error(R.drawable.nasa_api)
            }
            findViewById<TextView>(R.id.explanationR).text = pictureDTO.explanation
            findViewById<TextView>(R.id.date_pictureR).text = pictureDTO.date
        }
    }
}