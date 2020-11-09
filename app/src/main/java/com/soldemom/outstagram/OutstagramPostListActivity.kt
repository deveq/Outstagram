package com.soldemom.outstagram

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.soldemom.outstagram.fragments.*
import com.soldemom.outstagram.recyclerview.PostAdapter
import com.soldemom.outstagram.retrofit.Post
import kotlinx.android.synthetic.main.activity_outstagram_post_list.*
import kotlinx.android.synthetic.main.fragment_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

//        val aa = contentResolver.openInputStream(imgUri)
//
//        val extension: String = imgPath.subString(imgPath.lastIndexOf("."))
//
//        val localImageFile = File(applicationContext.filesDir,"localImgFile${extension}")



        val path = getExternalFilesDir(
            Environment.DIRECTORY_PICTURES.toString() +
                File.separator + "Output Folder")
        val file = File(getExternalFilesDir(null), "filename.jpg")



        masterApplication = application as MasterApplication

        //전체 포스팅 받아오기.
        masterApplication.service.getAllPosts().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                if (response.isSuccessful) {
                    val postList = response.body()
                    mainViewModel.postList = postList ?: arrayListOf<Post>()
                    Toast.makeText(this@OutstagramPostListActivity, "${mainViewModel.postList.size}", Toast.LENGTH_SHORT ).show()

                    val list = listOf<Fragment>(
                        AllPostingFragment(mainViewModel),
                        MyPostingFragment(mainViewModel),
                        UploadFragment(mainViewModel),
                        SettingFragment()
                    )

                    val fragmentAdapter = FragmentAdapter(this@OutstagramPostListActivity,list)
                    view_pager.adapter = fragmentAdapter

                    TabLayoutMediator(tab_layout, view_pager) { tab, position ->
                        tab.text = when (position) {
                            1 -> "내 포스팅"
                            2 -> "업로드"
                            3 -> "설정"
                            else -> "전체 포스팅"
                        }
                    }.attach()


                }

            }


            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("pathh","resultCode : $resultCode , requestCode : $requestCode ,, $RESULT_OK")

        if(requestCode == 1004 && resultCode == RESULT_OK) {
            //상대주소(와 비슷한) uri을 얻음
            val uri = data!!.data!!
            mainViewModel.filePath = getImageFilePath(uri)
            Log.d("pathh","path : ${mainViewModel.filePath}")

            mainViewModel.uploadContent = data.getStringExtra("content")

//            uploadPost(uploadFragContent)

        }
        //uri는 content://media/external/images/media/숫자
        //imageFilePath는 /storage/emulated/0/DCIM/Camera/사진이름.jpg
        //uri는 실제로 해당 이미지에 접근할 수 있는 주소가 아니므로 절대경로를 구해온거


    }

    //uri를 받은 후 절대경로를 얻기 위한 메서드
    fun getImageFilePath(contentUri: Uri): String {
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null, null, null)
        if (cursor!!.moveToNext()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)

    }

    //이 함수가 UploadFragment에서 실행되야하는데..
    fun uploadPost(uploadContent: String) {
        val file = File(mainViewModel.filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image",file.name, fileRequestBody)
        //이미지는 큰 용량때문에 파트를 나눠서 보내므로 multiPart로 보냄.

        val content = RequestBody.create(MediaType.parse("text/plain"), uploadContent)
        //

        masterApplication.service.uploadPost(part, content).enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {

                    //제대로 보내진거 맞는지 확인
                    val post = response.body()
                    Log.d("pathh","${post!!.content}")

                    //내가 포스트한 목록으로 가기.


                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
            }
        })


    }


}