package com.soldemom.outstagram

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.soldemom.outstagram.retrofit.User
import kotlinx.android.synthetic.main.activity_email_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignInActivity : AppCompatActivity() {


    lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_in)

        signin_username_inputbox
        signin_password_inputbox
        signin_button

        masterApplication = application as MasterApplication


        signup_button.setOnClickListener {
            startActivity(Intent(this,EmailSignUpActivity::class.java))
        }

        logout.setOnClickListener {
            logout()
        }

        signin_button.setOnClickListener {
            val username = signin_username_inputbox.text.toString()
            val password = signin_password_inputbox.text.toString()

//            val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
//            val token = sp.getString("login_sp","")
//            Log.d("LoginActivity","token : $token")

            //Activity가 onCreate될 때 masterApplication의 onCreate가 동작해 RetrofitService객체인
            //service가 초기화 되므로 createRetrofit()메서드를 미리 호출할 필요가 없음.
            //(로그인이 안되있다면)service에는 token이 없는 상태
            masterApplication.service
                .login(username, password)
                .enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        val token = user?.token

                        saveUserToken(token!!)

                        //header에 token이 없이 만들어져있었으므로
                        //token을 가진 상태로 다시 createRetrofit
                        masterApplication.createRetrofit()

                        Toast.makeText(
                            this@EmailSignInActivity,
                            "로그인 되었습니다.",
                            Toast.LENGTH_SHORT)
                            .show()


                        val intent = Intent(
                            this@EmailSignInActivity,
                            OutstagramPostListActivity::class.java)
                        startActivity(intent)

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {


                }
            })

        }


    }

    fun saveUserToken(token: String) {
        val sp = getSharedPreferences("login_sp",Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp",token)
        editor.commit()
    }

    fun logout(){
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove("login_sp")
        editor.commit()


        masterApplication.createRetrofit()
        Toast.makeText(this,"${masterApplication.token} 오또케 됫나요",Toast.LENGTH_SHORT).show()
    }




}