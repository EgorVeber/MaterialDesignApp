package ru.gb.veber.materialdesignapp.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


/**
 * Кастомный Behavior, позволяющий View занимать ровно то место, которое осталось между AppBarLayout и нижнем краем экрана
 */
class ViewBelowAppBarBehavior @JvmOverloads constructor(
        context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    )= dependency is AppBarLayout

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val appBar = dependency as AppBarLayout
        // т.к. y - отрицательное число
        val currentAppBarHeight = appBar.height + appBar.y
        val parentHeight = parent.height ?: 0
        val placeHolderHeight = (parentHeight - currentAppBarHeight).toInt()
        child.layoutParams?.height = placeHolderHeight
        child.requestLayout()
        return false
    }

}