package com.soldemom.outstagram.retrofit

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Post(
    val owner: String? = null,
    val content: String? = null,
    val image: String? = null
) : Serializable