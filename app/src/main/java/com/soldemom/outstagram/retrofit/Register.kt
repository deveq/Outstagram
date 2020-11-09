package com.soldemom.outstagram.retrofit

import java.io.Serializable

class Register(
    val username: String? = null,
    val password1: String? = null,
    val password2: String? = null
) : Serializable