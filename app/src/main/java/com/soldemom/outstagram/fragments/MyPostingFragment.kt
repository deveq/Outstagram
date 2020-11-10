package com.soldemom.outstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldemom.outstagram.MainViewModel
import com.soldemom.outstagram.MasterApplication
import com.soldemom.outstagram.R
import com.soldemom.outstagram.recyclerview.PostAdapter
import com.soldemom.outstagram.retrofit.Post
import kotlinx.android.synthetic.main.fragment_my_posting.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPostingFragment(val mainViewModel: MainViewModel) : Fragment() {

    lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_my_posting, container, false)

        val adapter = PostAdapter(mainViewModel.myPostList)

        fragmentView.my_posting_recycler_view.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        return fragmentView
    }

    override fun onResume() {
        super.onResume()

        (fragmentView.my_posting_recycler_view.adapter as PostAdapter).apply {
            postList = mainViewModel.myPostList
            notifyDataSetChanged()
        }
    }
}