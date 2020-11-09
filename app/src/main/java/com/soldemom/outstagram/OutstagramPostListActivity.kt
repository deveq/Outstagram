package com.soldemom.outstagram

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.soldemom.outstagram.fragments.*
import com.soldemom.outstagram.recyclerview.PostAdapter
import com.soldemom.outstagram.retrofit.Post
import kotlinx.android.synthetic.main.activity_outstagram_post_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutstagramPostListActivity : AppCompatActivity() {

    val glide: Glide? = null

    lateinit var masterApplication: MasterApplication
    val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outstagram_post_list)

/*        Glide.with(this)
            .load("주소")
            .into(뷰)*/


        masterApplication = application as MasterApplication

//        val adapter = PostAdapter(arrayListOf<Post>())

        view_pager





        masterApplication.service.getAllPosts().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                if (response.isSuccessful) {
                    val postList = response.body()
                    mainViewModel.postList = postList ?: arrayListOf<Post>()
                    Toast.makeText(this@OutstagramPostListActivity, "${mainViewModel.postList.size}", Toast.LENGTH_SHORT ).show()

//                    adapter.postList = postList ?: arrayListOf<Post>()
//                    adapter.notifyDataSetChanged()

                    val list = listOf<Fragment>(
                        AllPostingFragment(mainViewModel),
                        MyPostingFragment(mainViewModel),
                        UploadFragment(),
                        SettingFragment()
                    )

                    val fragmentAdapter = FragmentAdapter(this@OutstagramPostListActivity,list)
                    view_pager.adapter = fragmentAdapter
                }


            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
            }
        })



//        post_recycler_view.adapter = adapter
//        post_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.menu_logout) {
            logout()
            val intent = Intent(this,EmailSignInActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove("login_sp")
        editor.commit()
        masterApplication.createRetrofit()
    }
}