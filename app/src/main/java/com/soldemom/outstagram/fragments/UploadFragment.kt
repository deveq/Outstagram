package com.soldemom.outstagram.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.soldemom.outstagram.MainViewModel
import com.soldemom.outstagram.MasterApplication
import com.soldemom.outstagram.OutstagramPostListActivity
import com.soldemom.outstagram.R
import com.soldemom.outstagram.retrofit.Post
import kotlinx.android.synthetic.main.fragment_upload.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadFragment(val viewModel: MainViewModel) : Fragment() {

    lateinit var masterApplication: MasterApplication
    lateinit var uploadContent: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        masterApplication = requireActivity().application as MasterApplication

        val view = inflater.inflate(R.layout.fragment_upload, container, false)

        view.view_pictures.setOnClickListener {

            getPicture(view)

        }

        view.upload.setOnClickListener {

            val filePath = viewModel.filePath
            uploadContent = view.content_input.text.toString()

            filePath?.let {
                uploadPost(it)


            }



        }



        return view
    }

    //이미지 가져오기. 이미지 가져오기 역시 다른 액티비티를 이용하는것이므로
    //intent를 사용해서 의뢰해야함.
    fun getPicture(view: View) {
        val content = view.content_input.text.toString()

        val intent = Intent(Intent.ACTION_PICK)
        //외부저장소로 intent
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        //외부저장소로 갔을때 이미지만 나오게끔.
        intent.type = "image/*"
        intent.putExtra("content",content)
        requireActivity().startActivityForResult(intent, 1004)
    }

    fun uploadPost(filePath: String) {

        Log.d("pathh","${filePath}랑 $uploadContent")

        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image",file.name, fileRequestBody)
        //이미지는 큰 용량때문에 파트를 나눠서 보내므로 multiPart로 보냄.

        val content = RequestBody.create(MediaType.parse("text/plain"), uploadContent)
        //

        Log.d("pathh","${file.exists()}, ${file.name}, ${file.path}")

        masterApplication.service.uploadPost(part, content).enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {

                Log.d("pathh","여긴 들어왔나용?")


                if (response.isSuccessful) {

                    //제대로 보내진거 맞는지 확인
                    val post = response.body()
                    viewModel.filePath = null
                    val intent = Intent(context,OutstagramPostListActivity::class.java)
                    Toast.makeText(context,"업로드 성공",Toast.LENGTH_SHORT).show()
                    startActivity(intent)


                    //내가 포스트한 목록으로 가기.
                    //.... 어떻게 가요...?



                }
            }




            override fun onFailure(call: Call<Post>, t: Throwable) {

                Log.d("pathh","그럼 실패인가용?")
            }
        })


    }

}