package com.dm.dll.proyectoindividual.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.dm.dll.proyectoindividual.databinding.ActivityLoginBinding
import com.dm.dll.proyectoindividual.databinding.ActivityMain2Binding
import com.dm.dll.proyectoindividual.databinding.ActivityRegistroBinding
import com.dm.dll.proyectoindividual.ui.viewmodels.LoginViewModel
import com.dm.dll.proyectoindividual.ui.viewmodels.RegistroViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class RegistroActivity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var executor: Executor
    private val registroViewModel: RegistroViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initObservables()

    }

    private fun initObservables() {
        registroViewModel.user.observe(this){
            startActivity(Intent(this,LoginActivity::class.java))//esta es la vista que me sale a crear cuenta?
        }

        registroViewModel.error.observe(this){
            Snackbar.make(
                this,
                binding.edtText,
                it,
                Snackbar.LENGTH_LONG
            ).show()


        }
    }

    private fun initListeners(){
        binding.btnRegistrarse.setOnClickListener {
            registroViewModel.createUserWithEmailAndPassword(
                binding.m3TietEmail.text.toString(),
                binding.m3TietContrasenia.text.toString(),
                binding.m3TietUser.text.toString()
            )

        }

    }






}