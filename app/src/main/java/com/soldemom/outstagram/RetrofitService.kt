package com.soldemom.outstagram

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET
    fun getAAA(
        @Query("repo/{name}")name: String
    ) : Call<List<String>>
}