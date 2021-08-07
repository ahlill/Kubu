package com.example.anyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.anyam.databinding.ActivityLoginBinding
import com.example.authfirebaseapp.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            fabLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    email.isBlank() -> {
                        Toast.makeText(applicationContext, "Isi kolom email", Toast.LENGTH_SHORT).show()
                        etEmail.requestFocus()
                    }
                    password.isBlank() -> {
                        Toast.makeText(applicationContext, "Isi kolom password", Toast.LENGTH_SHORT).show()
                        etPassword.requestFocus()
                    }
                    else -> {
                        loginUser(email, password)
                    }
                }
            }

            btnRegister.setOnClickListener {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(
                this
        ) { task ->
            if (task.isSuccessful) {
//                Log.d("TAG", "Sigin with email: succes ${data?.additionalUserInfo?.username}")
                val user = mAuth?.currentUser
                val nama = user?.displayName

                val verifikasi = user?.isEmailVerified

                if (verifikasi == true) {
                    Toast.makeText(this, "Selamat datang $nama", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, IntroActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "email kamu belum terverifikasi", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w("TAG", "Sing in with email:failure")
                Toast.makeText(this, "Email kamu belum terdaftar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}