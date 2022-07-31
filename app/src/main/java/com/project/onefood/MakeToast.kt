package com.project.onefood

import android.content.Context
import android.widget.Toast


fun showtoast(context : Context, string: String){
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}