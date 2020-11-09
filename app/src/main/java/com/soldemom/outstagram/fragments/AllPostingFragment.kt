package com.soldemom.outstagram.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.soldemom.outstagram.MainViewModel
import com.soldemom.outstagram.MasterApplication
import com.soldemom.outstagram.R
import com.soldemom.outstagram.recyclerview.PostAdapter
import kotlinx.android.synthetic.main.fragment_all_posting.view.*

class AllPostingFragment(val mainViewModel: MainViewModel) : Fragment() {


    lateinit var masterApplication: MasterApplication
    lateinit var fragmentListener: FragmentInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_all_posting, container, false)

        val postList = mainViewModel.postList
        Log.d("으앙","${postList.size}")

        view.all_posting_recycler_view.adapter = PostAdapter(postList)
        view.all_posting_recycler_view.layoutManager = LinearLayoutManager(context)

        return view
    }



//
//
//        view.all_posting_recycler_view.apply {
//            adapter = PostAdapter(arrayListOf<Post>())
//            //Fragment는 Activity가 아니기 때문에 Context를 사용할 수 없으므로
//            //this 대신 activity 객체를 사용
//            layoutManager = LinearLayoutManager(activity)
//        }
//
//
//
//
//
//    }


}