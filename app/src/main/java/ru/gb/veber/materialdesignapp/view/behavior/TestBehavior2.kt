package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import ru.gb.veber.materialdesignapp.utils.MyImageView
import show
import kotlin.math.abs

class TestBehavior2(context: Context, attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency is ConstraintLayout) or (dependency is MyImageView)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is ConstraintLayout) {
            Log.d("dependency", "child = " + child.y.toString())
            Log.d("dependency", "dependency = " + dependency.y.toString())
            // child.x = (dependency.width.toFloat()-child.width)+dependency.y*2
            //child.x = dependency.y -child.y -child.width
        }
        if (dependency is MyImageView) {
            child.y = dependency.y + dependency.height
            Log.d("MyImageView", "child = " + child.y.toString())
            Log.d("MyImageView", "dependency = " + dependency.y.toString())
            child.x = (dependency.width.toFloat()-child.width)+dependency.y/2
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}