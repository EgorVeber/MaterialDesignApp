package ru.gb.veber.materialdesignapp.view.planets

import EarthState
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import coil.transform.CircleCropTransformation
import hide
import kotlinx.android.synthetic.main.fragment_planets.view.*
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPlanetsBinding
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.viewmodel.EarthViewModel
import show

class EarthFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!
    private var flagImage = false

    private val planetsViewModel: EarthViewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planetsViewModel.getLiveData().observe(viewLifecycleOwner) { render(it) }
        planetsViewModel.getEpic()


        binding.imageView.setOnClickListener {
            changeBoundsTransitionImage()
        }
    }

    private fun changeBoundsTransitionImage() {
        TransitionSet().also { transition ->
            transition.duration = 1000L
            transition.addTransition(ChangeBounds())
            transition.addTransition(ChangeImageTransform())
            TransitionManager.beginDelayedTransition(binding.root, transition)
        }
        flagImage = !flagImage
        with(binding.imageView) {
            if (flagImage) {
                scaleType = ImageView.ScaleType.CENTER_CROP
                (layoutParams as ConstraintLayout.LayoutParams).height =
                    ConstraintLayout.LayoutParams.MATCH_PARENT
            } else {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                (layoutParams as ConstraintLayout.LayoutParams).height =
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            }
        }
    }

    private fun render(appState: EarthState) {


        val constraintSetStart = ConstraintSet()
        constraintSetStart.clone(binding.root)
        val changeBounds = ChangeBounds()
        changeBounds.duration = 1000L
        changeBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
        TransitionManager.beginDelayedTransition(binding.root, changeBounds)
        constraintSetStart.connect(
            R.id.image_view,
            ConstraintSet.END,
            R.id.constrainPlanetsMain,
            ConstraintSet.START
        )
        constraintSetStart.connect(
            R.id.title,
            ConstraintSet.TOP,
            R.id.constrainPlanetsMain,
            ConstraintSet.BOTTOM
        )


        constraintSetStart.applyTo(binding.root)


        when (appState) {
            is EarthState.Error -> {
                binding.imageView.load(R.drawable.nasa_api)
            }
            is EarthState.Loading -> {
                binding.imageView.load(R.drawable.loading1) {
                    crossfade(CROSS_FADE_500)
                }
            }
            is EarthState.Success -> {
                val strDate = appState.serverResponseData.last().date.split(" ").first()
                val image = appState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-", "/", true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.imageView.load(url) {
                    transformations(CircleCropTransformation())
                }
                binding.title.text = appState.serverResponseData.last().caption
                binding.date.text = appState.serverResponseData.last().date

                constraintSetStart.connect(
                    R.id.image_view,
                    ConstraintSet.END,
                    R.id.constrainPlanetsMain,
                    ConstraintSet.END
                )

                constraintSetStart.connect(
                    R.id.title,
                    ConstraintSet.TOP,
                    R.id.image_view,
                    ConstraintSet.BOTTOM
                )
                constraintSetStart.setMargin(R.id.image_view, ConstraintSet.START, 20)
                constraintSetStart.setMargin(R.id.image_view, ConstraintSet.END, 20)
                constraintSetStart.applyTo(binding.root)

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
            EarthFragment()
    }
}