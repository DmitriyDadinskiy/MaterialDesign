package com.kotlinmovie.materialdesign.ui.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.kotlinmovie.materialdesign.R
import kotlin.math.abs

class ButtonBehavior(context: Context, attr: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(//функция поиска зависимостей
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) = dependency is AppBarLayout//зависимость поймана и это AppBarLayout

    override fun onDependentViewChanged(// изменение зависимости
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val bar = dependency as AppBarLayout
        var barHeight = bar.height.toFloat()//высота бара
        var barY = bar.y


        if (abs(barY) > (barHeight * 2 / 3)) {

            child.x = (dependency.height / 5).toFloat()
        } else {
            child.x = ((dependency.width / 1.25).toFloat())
            child.scrollBarFadeDuration = 1000
            child.alpha = ((barHeight * 2 / 3) - abs(barY / 2)) / (barHeight * 2 / 3)
        }

        return super.onDependentViewChanged(parent, child, dependency)

    }


}
