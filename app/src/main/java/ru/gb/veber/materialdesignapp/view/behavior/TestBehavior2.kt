package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
            Log.d("Counter", "dependency.y = " + dependency.y.toString());
            Log.d("Counter", "dependency.heigh = " + dependency.height.toString());
            Log.d("Counter", "dependency.widthy = " + dependency.width.toString());
            Log.d("Counter", "child.x = " + child.x.toString());
            child.alpha = 1 - dependency.height / dependency.y / 2 + 0.5F
            //   child.x = (dependency.width.toFloat()-child.width)+dependency.y*2
            //child.x = dependency.y -child.y -child.width
            child.x = (dependency.height - dependency.y) - child.height / 2
            //  child.y = -0.9F * (dependency.y / 2) + dependency.width


        }
        if (dependency is MyImageView) {

            child.y = dependency.y - child.height
            // child.y = dependency.y + dependency.height
            //child.x = dependency.y/child.width
            // child.x = (dependency.width.toFloat()-child.width)+dependency.y/2
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}