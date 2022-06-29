package ru.gb.veber.materialdesignapp.view.old

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.veber.materialdesignapp.databinding.FragmentCoordinatorBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentCoordinatorOldBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureBinding
import ru.gb.veber.materialdesignapp.utils.WIKIPEDIA

class PictureFragment : Fragment() {
    private var _binding: FragmentCoordinatorBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  binding.inputLayout.setEndIconOnClickListener { clickWiki() }
    }


    private fun clickWiki() {
//        startActivity(Intent(Intent.ACTION_VIEW).apply {
//            data = Uri.parse(WIKIPEDIA + binding.inputEditText.text.toString())
//        })
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