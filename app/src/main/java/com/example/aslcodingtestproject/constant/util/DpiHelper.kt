package com.example.aslcodingtestproject.constant.util

import android.content.Context
import android.util.DisplayMetrics

// Dpi related obj
object DpiHelper {
    fun getDeviceDensityString(context: Context): String {
        return when (context.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "ldpi"
            DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> "hdpi"
            DisplayMetrics.DENSITY_260,
            DisplayMetrics.DENSITY_280,
            DisplayMetrics.DENSITY_300,
            DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            DisplayMetrics.DENSITY_340,
            DisplayMetrics.DENSITY_360,
            DisplayMetrics.DENSITY_400,
            DisplayMetrics.DENSITY_420,
            DisplayMetrics.DENSITY_440,
            DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            DisplayMetrics.DENSITY_560,
            DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            else -> ""
        }
    }
}