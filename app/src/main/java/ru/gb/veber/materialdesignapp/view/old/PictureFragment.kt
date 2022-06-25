package ru.gb.veber.materialdesignapp.view.old

import PictureViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureBinding
import ru.gb.veber.materialdesignapp.utils.WIKIPEDIA

class PictureFragment : Fragment() {
    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.inputLayout.setEndIconOnClickListener { clickWiki() }
    }

    private fun init() {
        binding.inputLayout.setEndIconOnClickListener { clickWiki() }
    }

    private fun clickWiki() {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(WIKIPEDIA + binding.inputEditText.text.toString())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PictureFragment()
    }
}