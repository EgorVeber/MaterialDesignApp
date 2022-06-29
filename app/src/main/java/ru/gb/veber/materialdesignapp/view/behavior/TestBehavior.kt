package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
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

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is ConstraintLayout) {

            child.y = -1 * (dependency.y / 2) + dependency.width
            child.alpha = 1 - dependency.height / dependency.y/2  + 0.5F
            // child.y = (dependency.width.toFloat() - child.width) + dependency.y * 2
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}