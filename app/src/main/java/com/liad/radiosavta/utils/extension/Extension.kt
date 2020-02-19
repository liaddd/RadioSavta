package com.liad.radiosavta.utils.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.liad.radiosavta.R

fun changeFragment(
    fragmentManager: FragmentManager, @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false
) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    if (addToBackStack) fragmentTransaction.addToBackStack(null)
    fragmentTransaction.setCustomAnimations(
        R.anim.abc_fade_in,
        R.anim.abc_shrink_fade_out_from_bottom,
        R.anim.abc_grow_fade_in_from_bottom,
        R.anim.abc_popup_exit
    )
    fragmentTransaction.replace(containerId, fragment, fragment::class.java.simpleName).commit()
}


fun toast(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}

fun <T> MutableList<T>.clearAndAddAll(newData: List<T>) {
    clear()
    addAll(newData)
}

fun convertIntToDay(day: Int): String = when (day) {
    0 -> "Sundays"
    1 -> "Mondays"
    2 -> "Tuesdays"
    3 -> "Wednesdays"
    4 -> "Thursdays"
    5 -> "Fridays"
    6 -> "Saturdays"
    else -> "not implemented"
}