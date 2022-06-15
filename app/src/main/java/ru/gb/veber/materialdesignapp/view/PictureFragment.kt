package ru.gb.veber.materialdesignapp.view

import AppState
import BottomNavigationDrawerFragment
import PictureVM
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import hide
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureBinding
import ru.gb.veber.materialdesignapp.utils.*
import show
import java.text.SimpleDateFormat
import java.util.*

class PictureFragment : Fragment() {


    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!
    private lateinit var bSheetB: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PictureVM by lazy {
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
        initView()

        viewModel.setLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendServerRequest(Date().formatDate())
    }

    private fun initView() {
        setHasOptionsMenu(true)
        (activity as MainActivity).setSupportActionBar(binding.bottomAppBar)

        with(binding)
        {
            inputLayout.setEndIconOnClickListener { clickWiki() }

            fab.setOnClickListener { clickFub() }


            chip1.setOnClickListener { viewModel.sendServerRequest(takeDate(0)) }
            chip2.setOnClickListener { viewModel.sendServerRequest(takeDate(-1)) }
            chip3.setOnClickListener { viewModel.sendServerRequest(takeDate(-2)) }


            bSheetB = BottomSheetBehavior.from(lifeHack.bottomSheetContainer).apply {
                addBottomSheetCallback(callBackBehavior)
            }
        }
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }


    private fun renderData(appState: AppState) {
        with(binding)
        {
            when (appState) {
                is AppState.Error -> {
                    lifeHack.title.text = getString(R.string.Error)
                    lifeHack.explanation.text = appState.errorMessage
                    imageView.load(R.drawable.nasa_api)
                }
                is AppState.Loading -> {
                    lifeHack.title.text = getString(R.string.loading)
                    imageView.load(R.drawable.loading1) {
                        crossfade(CROSS_FADE_500)
                    }
                }
                is AppState.Success -> {
                    lifeHack.title.text = appState.pictureDTO.title
                    lifeHack.explanation.text = appState.pictureDTO.explanation
                    if(appState.pictureDTO.mediaType =="video"){
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(appState.pictureDTO.url)
                        })
                    }
                    imageView.load(appState.pictureDTO.hdurl) {
                        placeholder(R.drawable.loading1)
                        crossfade(CROSS_FADE_500)
                        error(R.drawable.nasa_api)
                    }
                    lifeHack.datePicture.text = appState.pictureDTO.date
                }
                else -> {}
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickChips(key: String) {
        when (key) {
            CHIP_TODAY -> viewModel.sendServerRequest(Date().formatDate())
            CHIP_YESTERDAY -> viewModel.sendServerRequest(DateMinusDay(KEY_CHIP_YESTERDAY))
            CHIP_BEFORE_YD -> viewModel.sendServerRequest(DateMinusDay(KEY_CHIP_BEFORE_YD))
        }
    }

    private fun clickFub() {
        when (bSheetB.state) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                behaviorCollapsed()
                bSheetB.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                behaviorCollapsed()
                bSheetB.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                behaviorExpanded()
                bSheetB.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }
        }
    }

    private val callBackBehavior = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    behaviorCollapsed()
                    binding.bottomAppBar.show()
                    binding.fab.show()
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    behaviorExpanded()
                    binding.bottomAppBar.hide()
                    binding.fab.hide()
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            binding.inputLayout.apply {
                if (slideOffset > SLIDE_OFF_SET) this.hide() else this.show()
            }
            binding.fab.apply {
                if (slideOffset > SLIDE_OFF_SET_TOP) this.hide() // временно , можно убрать
            }
        }
    }

    private fun behaviorCollapsed() {
        with(binding)
        {
            bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                requireContext(), R.drawable.ic_hamburger_menu_bottom_bar
            )
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_up))
            bottomAppBar.replaceMenu(R.menu.menu_bottom_app_bar)
        }
    }

    private fun behaviorExpanded() {
        with(binding)
        {
            bottomAppBar.navigationIcon = null
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_down))
            bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other)
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
                Toast.makeText(
                    context, "Settings",
                    Toast.LENGTH_SHORT
                ).show()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "")
                }
            }
        }
        return super.onOptionsItemSelected(item)
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