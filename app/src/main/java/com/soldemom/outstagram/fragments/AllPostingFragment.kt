package com.soldemom.outstagram.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldemom.outstagram.MainViewModel
import com.soldemom.outstagram.MasterApplication
import com.soldemom.outstagram.R
import com.soldemom.outstagram.recyclerview.PostAdapter
import kotlinx.android.synthetic.main.fragment_all_posting.view.*

class AllPostingFragment(val mainViewModel: MainViewModel) : Fragment() {


    lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_all_posting, container, false)

        val postList = mainViewModel.allPostList

        recyclerView = view.all_posting_recycler_view.apply{
            all_posting_recycler_view.adapter = PostAdapter(postList)
            all_posting_recycler_view.layoutManager = LinearLayoutManager(context)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        Log.d("pathh","AllPostring")

    }
}