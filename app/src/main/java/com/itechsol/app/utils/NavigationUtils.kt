package com.itechsol.app.utils

import android.app.Activity
import com.itechsol.app.R

object NavigationUtils {
    enum class Animation {
        GO,
        BACK
    }

    fun animateTop(activity: Activity, animation: Animation) {
        if (animation == Animation.GO) {
            activity.overridePendingTransition(
                R.anim.open_top,
                R.anim.close_bottom
            )
        } else {
            activity.overridePendingTransition(
                R.anim.open_bottom,
                R.anim.close_top
            )
            activity.finish()
        }
    }

    fun animate(activity: Activity, animation: Animation) {
        if (animation == Animation.GO) {
            activity.overridePendingTransition(
                R.anim.open_next,
                R.anim.close_previous
            )
        } else {
            activity.overridePendingTransition(
                R.anim.open_previous,
                R.anim.close_next
            )
            activity.finish()
        }
    }
}