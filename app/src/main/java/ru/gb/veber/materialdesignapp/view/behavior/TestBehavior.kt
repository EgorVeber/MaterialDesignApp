package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.gb.veber.materialdesignapp.utils.MyImageView
import kotlin.math.abs

class TestBehavior(context: Context, attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is ConstraintLayout
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is ConstraintLayout) {
            child.layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
            child.foregroundGravity = Gravity.CENTER
            child.y = -0.85F * (dependency.y / 2) + dependency.width
            child.alpha = 1 - dependency.height / dependency.y / 2 + 0.5F

            var params = (dependency.layoutParams as CoordinatorLayout.LayoutParams)
            val behavior = params.behavior as BottomSheetBehavior

            //  Log.d("BottomSheetBehavior","behavior.state = "+ behavior.state)
            if (behavior.state == BottomSheetBehavior.STATE_SETTLING) {
                //      Log.d("BottomSheetBehavior","if  behavior.state = "+ behavior.state)
                behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}