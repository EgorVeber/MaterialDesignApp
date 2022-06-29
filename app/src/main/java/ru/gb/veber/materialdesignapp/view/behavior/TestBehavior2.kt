package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import ru.gb.veber.materialdesignapp.utils.MyImageView
import kotlin.math.abs

class TestBehavior2(context: Context, attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is MyImageView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is MyImageView) {

            Log.d("MyImageView", dependency.y.toString())
            Log.d("MyImageView", "child = " + child.y.toString())

        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}