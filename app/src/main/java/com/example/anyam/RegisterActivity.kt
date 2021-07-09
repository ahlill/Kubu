package com.example.authfirebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.anyam.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        with(binding) {

            fabRegister.setOnClickListener {
                var email = etEmail.text.toString().trim()
                var password = etPassword.text.toString().trim()

                if (email.equals("")) {
                    Toast.makeText(applicationContext, "Isi kolom email", Toast.LENGTH_SHORT).show()
                    etEmail.requestFocus()
                } else if (password.equals("")) {
                    Toast.makeText(applicationContext, "Isi kolom password", Toast.LENGTH_SHORT).show()
                    etPassword.requestFocus()
                } else {
                    registerUser(email, password)
                }
            }

            btnLogin.setOnClickListener {
                finish()
//                val intent = Intent(applicationContext, LoginActivity::class.java)
//                startActivity(intent)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        Toast.makeText(this, "$email, $password", Toast.LENGTH_SHORT).show()
        Log.d("TAG", "$email, $password")
        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                Jika berhasil daftar
                    Log.d("Tag", "CreateUserWithEmail:succes")
                    sendEmail()

                    val user = mAuth?.currentUser

                    Toast.makeText(this, "Segera verifikasi akun baru kamu melalui link ynag di kirim ke Email kamu", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                } else {
//             Jika gagal daftar
                    Log.w("TAG", " Create User With Email:Failure", task.exception)
                    Toast.makeText(this, "Autentication failed1", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendEmail(){
        val user = Firebase.auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("mail", "Email sent.")
                }else{
                    Toast.makeText(applicationContext, "email gagal di kirim", Toast.LENGTH_SHORT).show()
                }
            }
    }
}