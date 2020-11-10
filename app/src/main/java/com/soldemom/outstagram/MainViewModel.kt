package com.soldemom.outstagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soldemom.outstagram.retrofit.Post

class MainViewModel : ViewModel() {
//    lateinit var postList: ArrayList<Post>
//    lateinit var post : LiveData<ArrayList<Post>>
    var allPostList : ArrayList<Post> = arrayListOf<Post>()
    var myPostList : ArrayList<Post> = arrayListOf<Post>()
    var filePath: String? = null
    var uploadContent: String? = null

}