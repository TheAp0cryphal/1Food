package com.project.onefood

import android.content.Context
import android.widget.Toast
import org.apache.commons.lang3.StringUtils;
import kotlin.math.max


fun showtoast(context : Context, string: String) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

fun findSimilarity(x : String, y : String): Double {
    var maxLength : Int = max(x.length, y.length)
    if (maxLength > 0){
        return ((maxLength - StringUtils.getLevenshteinDistance(x, y)).toDouble() / (maxLength).toDouble())
    }
    return 1.0
}

