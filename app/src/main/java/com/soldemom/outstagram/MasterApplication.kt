package com.soldemom.outstagram

import android.R
import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.soldemom.outstagram.retrofit.RetrofitService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {

    lateinit var service: RetrofitService
    var token: String? = null

    //manifest상에서 application이 먼저 실행됨.
    //application의 name속성에 .MasterApplication을 입력하면 application으로 MasterApplication이 실행됨.
    //여기의 onCreate에서 특정 객체를 생성하면 모든 액티비티에서 이용가능함.
    override fun onCreate() {
        super.onCreate()

        //Stetho를 초기화를 먼저 해야함.
        Stetho.initializeWithDefaults(this)

        createRetrofit()
        //chrome://inspect/#devices
        //stetho, glide, retrofit, gson,

    }

    fun createRetrofit() {

        val header = Interceptor {  //휴대폰에서부터 통신이 나갈때 그걸 가로챈 후
            val original = it.request()  //request를 받아 original이란 변수에 넣고

            val requestBuilder = original.newBuilder()
            val request : Request

            if (checkIsLogin()) {
                //로그인이 되어있다면
                token?.let {
                    requestBuilder.header("Authorization","token $token") //original에 header를 달아줌
                }
            }   //로그인이 되어있지 않다면 header를 달지 않고 바로 넘어감
            request = requestBuilder.build()

            it.proceed(request)     //개조 완료된 이후 다시 내보내줌
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor()) //Stetho가 낚아채도록 함
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    //위의 request에서 로그인을 했다면 token이 있을테니 header를 달아주는데,
    // 로그인을 하지 않았다면 달아줄 수 있는 token이 없으므로
    // SharedPreference에 token이 존재하는지 여부를 확인
    fun checkIsLogin() : Boolean {
        token = getUserToken()
        return token != "null"
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        return sp.getString("login_sp","null")
    }

}