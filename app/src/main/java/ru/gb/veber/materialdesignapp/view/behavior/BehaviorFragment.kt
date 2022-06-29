package ru.gb.veber.materialdesignapp.view.behavior

import PictureState
import PictureViewModel
import android.accounts.AbstractAccountAuthenticator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import hide
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.DateDialogBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentBehaviorBinding
import ru.gb.veber.materialdesignapp.utils.dataFromString
import ru.gb.veber.materialdesignapp.utils.formatDate
import show
import java.util.*


class BehaviorFragment : Fragment() {


    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    private var _binding: FragmentBehaviorBinding? = null
    private val binding: FragmentBehaviorBinding
        get() = _binding!!
    private var youTubePlayerView: YouTubePlayerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBehaviorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        youTubePlayerView = binding.youtubePlayer
        lifecycle.addObserver(youTubePlayerView!!)
    }

    private fun init() {
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendServerRequest(Date().formatDate())
        binding.inputEditText.setText(Date().formatDate())
        binding.inputLayout.setEndIconOnClickListener { showDialogDate() }
        binding.inputEditText.setOnClickListener { showDialogDate() }
        binding.closePlayer.setOnClickListener {
            binding.youtubePlayer.hide()
            binding.closePlayer.hide()
            binding.imageView.show()
        }
    }

    private fun showDialogDate() {

        val bindingDialog = DateDialogBinding.inflate(LayoutInflater.from(requireContext()))

        initDatePicker(
            dataFromString(binding.inputEditText.text.toString()) as Date,
            bindingDialog.inputDate
        )

        Dialog(requireContext()).apply { setContentView(bindingDialog.root) }.apply {
            bindingDialog.PositiveButtonDate.setOnClickListener {
                binding.inputEditText.setText(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                viewModel.sendServerRequest(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                this.dismiss()
            }
            this.show()
        }
    }


    private fun renderData(appState: PictureState) {
        with(binding)
        {
            when (appState) {
                is PictureState.Error -> {
                    title.text = getString(R.string.Error)
                    explanation.text = appState.errorMessage
                    imageView.load(R.drawable.nasa_api)
                }
                is PictureState.Loading -> {
                    imageView.load(R.drawable.loading1)
                }
                is PictureState.Success -> {
                    // showNasaVideo("https://www.youtube.com/embed/liapnqj9GDc?rel=0")
                    title.text = appState.pictureDTO.title
                    explanation.text = appState.pictureDTO.explanation
                    datePicture.text = appState.pictureDTO.date
                    if (appState.pictureDTO.mediaType == "video") {
                        imageView.load(R.drawable.nasa_api)
                        showNasaVideo(appState.pictureDTO.url!!)
                    } else {
                        imageView.load(appState.pictureDTO.hdurl) {
                            placeholder(R.drawable.loading1)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private fun showNasaVideo(url: String) {
        binding.youtubePlayer.show()
        binding.closePlayer.show()
        binding.imageView.hide()
        youTubePlayerView?.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(findVideoId(url), 0f)
            }
        })
    }


    private fun findVideoId(url: String): String {
//        2022-02-16 https://www.youtube.com/embed/liapnqj9GDc?rel=0
//        2022-02-09 https://www.youtube.com/embed/NS71ppsk7n0?rel=0
//        2022-03-30 https://www.youtube.com/embed/m8qvOpcDt1o?rel=0
//        2022-05-09 https://www.youtube.com/embed/aKK7vS2CHC8?rel=0
//        2022-05-29 https://www.youtube.com/embed/cNT5yAqpBmI?rel=0
        return url.substringAfterLast('/').substringBefore('?')
    }


    private fun initDatePicker(date: Date, datePicker: DatePicker) {
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        datePicker.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH],
            null
        )
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
        return Calendar.getInstance().apply {
            this[Calendar.YEAR] = datePicker.getYear()
            this[Calendar.MONTH] = datePicker.getMonth()
            this[Calendar.DAY_OF_MONTH] = datePicker.getDayOfMonth()
        }.time
    }

    companion object {
        @JvmStatic
        fun newInstance() = BehaviorFragment()
    }
}
