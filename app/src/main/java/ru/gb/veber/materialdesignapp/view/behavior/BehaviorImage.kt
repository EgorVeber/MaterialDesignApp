package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BehaviorImage(context: Context, attr: AttributeSet? = null) :
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

            child.y = -0.9F * (dependency.y / 2) + dependency.width
            child.alpha = 1 - dependency.height / dependency.y / 2 + 0.5F

            val behavior =
                ((dependency.layoutParams as CoordinatorLayout.LayoutParams).behavior
                        as BottomSheetBehavior)

//            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
//                behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//            }
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}