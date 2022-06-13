package ru.gb.veber.materialdesignapp.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.gb.m_1975_3.view.pictureoftheday.BottomNavigationDrawerFragment
import com.gb.m_1975_3.viewmodel.AppState
import com.gb.m_1975_3.viewmodel.PictureVM
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import kotlinx.android.synthetic.main.fragment_picture.*
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureBinding
import ru.gb.veber.materialdesignapp.utils.*
import java.time.LocalDate
import java.util.*

class PictureFragment : Fragment() {


    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!
    private var isMain = true
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    val viewModel: PictureVM by lazy {
        ViewModelProvider(this).get(PictureVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)

        viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendServerReques2(Date().formatDate())

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        setBottomSheetBehavior(binding.lifeHack.bottomSheetContainer)

        bottomSheetBehavior.addBottomSheetCallback(callBackBehavior)

        binding.fab.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            isMain = !isMain
//            if (!isMain) {
//                binding.bottomAppBar.navigationIcon = null
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_back_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other)
//            } else {
//                binding.bottomAppBar.navigationIcon =
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_hamburger_menu_bottom_bar
//                    )
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_plus_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_app_bar)
//            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.chip1.setOnClickListener { clickChips(CHIP_TODAY) }
            binding.chip2.setOnClickListener { clickChips(CHIP_YESTERDAY) }
            binding.chip3.setOnClickListener { clickChips(CHIP_BEFORE_YD) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickChips(key: String) {
        when (key) {
            CHIP_TODAY -> {
                viewModel.sendServerReques2(Date().formatDate())
            }
            CHIP_YESTERDAY -> {
                viewModel.sendServerReques2(
                    LocalDate.now().minusDays(KEY_CHIP_YESTERDAY).toString()
                )
            }
            CHIP_BEFORE_YD -> viewModel.sendServerReques2(
                LocalDate.now().minusDays(KEY_CHIP_BEFORE_YD).toString()
            )
        }
    }

    private val callBackBehavior = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    // TODO()
                }
                BottomSheetBehavior.STATE_DRAGGING -> {
                    // TODO()
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    //  TODO()
                }
                BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    //  TODO()
                }
                BottomSheetBehavior.STATE_HIDDEN -> {
                    //  TODO()
                }
                BottomSheetBehavior.STATE_SETTLING -> {
                    // TODO()
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            // bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(
                context, "Favourite",
                Toast.LENGTH_SHORT
            ).show()
            R.id.app_bar_settings -> {
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.container,
                    ChipsFragment()
                )?.addToBackStack(null)?.commit()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }


    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Error -> {/*TODO HW*/
            }
            is AppState.Loading -> {/*TODO HW*/
            }
            is AppState.Success -> {
                with(appState.pictureDTO)
                {
                    Log.d("@@@", "pictureDTO:")
                    Log.d("@@@", date.toString())
                    Log.d("@@@", explanation.toString())
                    Log.d("@@@", hdurl.toString())
                    Log.d("@@@", media_type.toString())
                    Log.d("@@@", service_version.toString())
                    Log.d("@@@", title.toString())
                    Log.d("@@@", url.toString())
                }
                binding.lifeHack.title.text = appState.pictureDTO.title
                binding.lifeHack.explanation.text = appState.pictureDTO.explanation
                binding.imageView.load(appState.pictureDTO.url)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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