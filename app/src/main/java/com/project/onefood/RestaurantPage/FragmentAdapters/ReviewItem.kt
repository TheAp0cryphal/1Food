package com.project.onefood.RestaurantPage.FragmentAdapters;

import android.os.Parcelable
import java.io.Serializable

data class ReviewItem(
        val author_name : String,
        val profile_photo_url : String,
        val rating : String,
        val text : String,
        val relative_time_description: String
): Serializable