package com.soldemom.outstagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.soldemom.outstagram.recyclerview.PostAdapter
import com.soldemom.outstagram.retrofit.Post
import kotlinx.android.synthetic.main.activity_outstagram_post_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutstagramPostListActivity : AppCompatActivity() {

    val glide: Glide? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outstagram_post_list)

/*        Glide.with(this)
            .load("주소")
            .into(뷰)*/


        val masterApplication = application as MasterApplication

        val adapter = PostAdapter(arrayListOf<Post>())


        masterApplication.service.getAllPosts().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                if (response.isSuccessful) {
                    val postList = response.body()
                    adapter.postList = postList ?: arrayListOf<Post>()
                    adapter.notifyDataSetChanged()

                }


            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
            }
        })



        post_recycler_view.adapter = adapter
        post_recycler_view.layoutManager = LinearLayoutManager(this)
    }
}