package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.gb.veber.materialdesignapp.utils.MyImageView

class BehaviorTextInput(context: Context, attr: AttributeSet? = null) :
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
            child.alpha = 1 - dependency.height / dependency.y / 2 + 0.5F
            child.x = (dependency.height - dependency.y) - child.height / 2
        }
        if (dependency is MyImageView) {
            child.y = dependency.y - child.height
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}