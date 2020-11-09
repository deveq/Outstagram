package com.soldemom.outstagram.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("json/students/")
    fun getStudentList(): Call<ArrayList<PersonFromServer>>

    @POST("json/students/")
    fun createStudents(
        @Body params: HashMap<String,Any>
    ) : Call<PersonFromServer>

    @POST("json/students/")
    fun createStudentEasy(
        @Body person: PersonFromServer
    ) : Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username")username: String,
        @Field("password1")password1: String,
        @Field("password2")password2: String
    ) : Call<User>

    //객체를 통째로 보낼때는 @Body만 쓰면 되지만
    //하나하나 보낼때는 @Field 뿐만 아니라
    //@FormUrlEncoded까지 적어줘야함.

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username")username: String,
        @Field("password")password: String
    ) : Call<User>
    //User는 username과 token이 있지만 해당 post요청의 반환값은 token만 돌아온다.
    //그래도 user객체에 token만 들어가져서 이용할 수 있음.

    @GET("instagram/post/list/all/")
    fun getAllPosts() : Call<ArrayList<Post>>

    @Multipart //파트가 여러개라는거
    @POST("instagram/post/")
    fun uploadPost(
        @Part Image: MultipartBody.Part,
        @Part ("Content") requestBody : RequestBody
    ) : Call<Post>

    //내 포스팅만 보기
    @GET("post/list/")
    fun getUserPostList(): Call<ArrayList<Post>>


}