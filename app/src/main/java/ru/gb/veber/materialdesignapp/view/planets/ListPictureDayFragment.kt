package ru.gb.veber.materialdesignapp.view.planets

import AppState
import ListPictureViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import hide
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureListBinding
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.utils.takeDate
import show
import java.util.*

class ListPictureDayFragment : Fragment() {

    private var _binding: FragmentPictureListBinding? = null
    private val binding get() = _binding!!

    private val listPictureViewModel: ListPictureViewModel by lazy {
        ViewModelProvider(this).get(ListPictureViewModel::class.java)
    }
    private val adapter: PictureAdapterR by lazy {
        PictureAdapterR()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pictureRecyclerView.adapter = adapter
        listPictureViewModel.getLiveData().observe(viewLifecycleOwner) { render(it) }
        listPictureViewModel.sendServerRequest(
            takeDate(-30),
            takeDate(-1)
        )//Последний месяц пока так
    }

    private fun render(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.imageView.show()
                binding.imageView.load(R.drawable.nasa_api) {
                }
            }
            is AppState.Loading -> {
                binding.imageView.show()
                binding.imageView.load(R.drawable.loading1) {
                    crossfade(CROSS_FADE_500)
                }
            }
            is AppState.SuccessListPicture -> {
                binding.imageView.hide()
                adapter.setList(appState.pictureList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListPictureDayFragment()
    }
}