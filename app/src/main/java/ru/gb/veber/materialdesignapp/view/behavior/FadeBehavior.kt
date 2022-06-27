package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class FadeBehavior(context: Context, attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is AppBarLayout) {
            child.alpha = 1 - abs(dependency.y  / dependency.height.toFloat())
            child.x = (dependency.width.toFloat()-child.width)+dependency.y*2
            //уехжаем вправо и скрываемся
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}