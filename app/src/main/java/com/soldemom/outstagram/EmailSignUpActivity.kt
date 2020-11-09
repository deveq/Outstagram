package com.soldemom.outstagram

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.soldemom.outstagram.retrofit.User
import kotlinx.android.synthetic.main.activity_email_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignUpActivity : AppCompatActivity() {

    var passwordChecked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_up)

        signup_username_inputbox
        signup_password_inputbox

        signup_password_confirm_inputbox.addTextChangedListener (
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val password1 = signup_password_inputbox.text.toString()
                    passwordChecked = (password1 == s.toString())
                }
            }
        )

        signup_button.setOnClickListener {
            //가입버튼
            register()
        }

    }

    //가입절차 진행
    fun register() {
        if (passwordChecked) {

            val userName = signup_username_inputbox.text.toString()
            val userPassword1 = signup_password_inputbox.text.toString()
            val userPassword2 = signup_password_confirm_inputbox.text.toString()

            val masterApplication = (application as MasterApplication)

            masterApplication.service
                .register(userName, userPassword1, userPassword2)
                .enqueue(
                    object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {


                    if(response.isSuccessful) {

                        Toast.makeText(
                            this@EmailSignUpActivity,
                            "가입에 성공하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                        val user = response.body()
                        val token = user?.token
                        saveUserToken(token!!)
                        masterApplication.createRetrofit()

                        val intent = Intent(
                            this@EmailSignUpActivity,
                            OutstagramPostListActivity::class.java)
                        startActivity(intent)

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        this@EmailSignUpActivity,
                        "가입에 실패하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        } else {
            AlertDialog.Builder(this)
                .setMessage("비밀번호를 확인해주세요")
                .setPositiveButton("확인",null)
                .create().show()
        }
    }


    // SharedPreference에 token을 저장함
    fun saveUserToken(token: String) {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp",token)
        editor.commit()
    }


}