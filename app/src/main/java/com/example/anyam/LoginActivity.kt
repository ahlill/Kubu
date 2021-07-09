package com.example.authfirebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.anyam.Intro
import com.example.anyam.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cart_item.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        with(binding) {
            fabLogin.setOnClickListener {
                var email = etEmail.text.toString().trim()
                var password = etPassword.text.toString().trim()

                if (email.equals("")) {
                    Toast.makeText(applicationContext, "Isi kolom email", Toast.LENGTH_SHORT).show()
                    etEmail.requestFocus()
                } else if (password.equals("")) {
                    Toast.makeText(applicationContext, "Isi kolom password", Toast.LENGTH_SHORT).show()
                    etPassword.requestFocus()
                } else {
                    loginUser(email, password)
                }
            }

            btnRegister.setOnClickListener {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        Log.d("TAG", "$email, $password")
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
//                Log.d("TAG", "Sigin with email: succes ${data?.additionalUserInfo?.username}")
                val user = mAuth?.currentUser
                val nama = user?.displayName

                val verifikasi = user?.isEmailVerified

                if (verifikasi == true) {
                    Toast.makeText(this,"Selamat datang ${nama}",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Intro::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext, "email kamu belum terverifikasi", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w("TAG", "Sing in with email:failure")
                Toast.makeText(this, "Email kamu belum terdaftar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}