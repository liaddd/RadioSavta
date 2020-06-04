package com.liad.radiosavta.utils.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.liad.radiosavta.R

@SuppressLint("PrivateResource")
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
    fragmentTransaction
        .replace(containerId, fragment, fragment::class.java.simpleName)
        .commit()
}

fun Activity.changeActivity(destination: Class<*>, closeCurrent: Boolean = false) {
    val intent = Intent(this, destination)
    startActivity(intent)
    if (closeCurrent) finish()
}

fun toast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun Context.log(message: String, tag: String = "Liad") {
    Log.d(tag, "${this::class.java.simpleName} $message")
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

fun String.removeSeconds(): String {
    val arr = if (this.contains(":")) this.split(":") else return this
    return "${arr[0]}:${arr[1]}"
}

fun View.show(show : Boolean = true){
    visibility = if(show) View.VISIBLE else View.GONE
}