package com.soldemom.outstagram

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MasterApplication : Application() {

    lateinit var service: RetrofitService

    //manifest상에서 application이 먼저 실행됨.
    //application의 name속성에 .MasterApplication을 입력하면 application으로 MasterApplication이 실행됨.
    //여기의 onCreate에서 특정 객체를 생성하면 모든 액티비티에서 이용가능함.
    override fun onCreate() {
        super.onCreate()


        //stetho, glide, retrofit, gson,

    }

    fun createRetrofit() {

        val header = Interceptor {  //휴대폰에서부터 통신이 나갈때 그걸 가로챈 후
            val original = it.request()  //request를 받아 original이란 변수에 넣고
            val request = original.newBuilder()  //original 개조 시작
                .header("Authorization","") //original에 header를 달아줌
                .build()
            it.proceed(request)     //모든 개조 완료된 이후 다시 내보내줌
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


        return false
    }




}