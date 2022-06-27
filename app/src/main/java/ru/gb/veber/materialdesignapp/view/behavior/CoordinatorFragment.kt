package ru.gb.veber.materialdesignapp.view.behavior

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.veber.materialdesignapp.databinding.FragmentCoordinatorBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentCoordinatorOldBinding


class CoordinatorFragment : Fragment() {
    private var _binding: FragmentCoordinatorOldBinding? = null
    private val binding: FragmentCoordinatorOldBinding
        get() = _binding!!

    var flag = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoordinatorOldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        (binding.btn.layoutParams as CoordinatorLayout.LayoutParams).behavior =
//            FadeBehavior(requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()
    }
}
