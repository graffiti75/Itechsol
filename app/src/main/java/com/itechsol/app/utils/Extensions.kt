package com.itechsol.app.utils

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

fun AppCompatActivity.typedArray(arrayId: Int) : TypedArray {
    return this.resources.obtainTypedArray(arrayId)
}

fun AppCompatActivity.openActivity(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    this.startActivity(intent)
    NavigationUtils.animate(this, NavigationUtils.Animation.GO)
}

fun AppCompatActivity.openActivityTopAnimation(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    this.startActivity(intent)
    NavigationUtils.animateTop(this, NavigationUtils.Animation.GO)
}

fun AppCompatActivity.openActivityForResult(code: Int, clazz: Class<*>) {
    val intent = Intent(this, clazz)
    this.startActivityForResult(intent, code)
    NavigationUtils.animate(this, NavigationUtils.Animation.GO)
}

fun AppCompatActivity.openActivityClearBackStack(clazz: Class<*>) {
    val intent = Intent(this, clazz)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
    NavigationUtils.animate(this, NavigationUtils.Animation.GO)
}

fun AppCompatActivity.openActivityExtras(clazz: Class<*>, key: String, value: Any) {
    val intent = Intent(this, clazz)
    val extras = getExtra(Bundle(), key, value)
    intent.putExtras(extras)

    this.startActivity(intent)
    NavigationUtils.animate(this, NavigationUtils.Animation.GO)
}

fun AppCompatActivity.openActivityExtras(clazz: Class<*>, keys: ArrayList<String>, values: ArrayList<Any>) {
    val intent = Intent(this, clazz)

    var extras = Bundle()
    val size = keys.size
    for (i in 0 until size) {
        val key = keys[i]
        val value = values[i]
        extras = getExtra(extras, key, value)
    }
    intent.putExtras(extras)

    this.startActivity(intent)
    NavigationUtils.animate(this, NavigationUtils.Animation.GO)
}

fun AppCompatActivity.openActivityForResultWithExtras(clazz: Class<*>, code: Int, key: String, value: Any) {
    val intent = Intent(this, clazz)
    val extras = getExtra(Bundle(), key, value)
    intent.putExtras(extras)

    this.startActivityForResult(intent, code)
    NavigationUtils.animate(this, NavigationUtils.Animation.GO)
}

private fun getExtra(extras: Bundle, key: String, value: Any): Bundle {
    when (value) {
        is String -> extras.putString(key, value)
        is Int -> extras.putInt(key, value)
        is Long -> extras.putLong(key, value)
        is Boolean -> extras.putBoolean(key, value)
    }
    return extras
}

@Suppress("DEPRECATION")
fun Context.networkOn(): Boolean {
    var result = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) result = true
                else if (type == ConnectivityManager.TYPE_MOBILE) result = true
            }
        }
    }
    return result
}
