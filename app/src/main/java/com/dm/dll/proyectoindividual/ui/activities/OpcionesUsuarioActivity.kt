package com.dm.dll.proyectoindividual.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.dm.dll.proyectoindividual.R

import com.dm.dll.proyectoindividual.databinding.ActivityOpcionesUsuarioBinding
import com.dm.dll.proyectoindividual.ui.adapters.NewsAdapter
import com.dm.dll.proyectoindividual.ui.viewmodels.LoginViewModel
import com.dm.dll.proyectoindividual.ui.viewmodels.OpcionesUsuarioViewModel
import com.dm.dll.proyectoindividual.ui.viewmodels.RegistroViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class OpcionesUsuarioActivity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth
    private lateinit var binding: ActivityOpcionesUsuarioBinding
    private lateinit var executor: Executor
    private val registroViewModel: OpcionesUsuarioViewModel by viewModels()
    private val adapter = NewsAdapter()
    private lateinit var dialog: AlertDialog

    private  var stringsData = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth
        getUserData()

        initVariables("")
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

    private fun initVariables(messageError:String) {
        dialog=AlertDialog.Builder(this)
            .setTitle(getString(R.string.error_carga))
            .setMessage(messageError)
            .setPositiveButton(getString(R.string.aceptar)){ dialog, _->
                //viewModel.getAllNobelPrizes()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancelar)){ dialog, _->
                dialog.dismiss()
            }
            .setCancelable(true) //si tocan afuera se cancela, false: si quero que tomen alguna de las opciones
            .create()



    }

    private fun initListeners(){



        binding.imgMenu.setOnClickListener {
            binding.inicioActivityConMenuLateral.openDrawer(GravityCompat.START)
        }

        binding.menuPrincipalNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.it_home -> {

                    startActivity(Intent(this@OpcionesUsuarioActivity,InicioActivity::class.java))

                }

                R.id.it_titulares -> {
                    startActivity(Intent(this@OpcionesUsuarioActivity,TitularesActivity::class.java))

                }

                R.id.it_buscar -> {
                    startActivity(Intent(this@OpcionesUsuarioActivity,FormularioNoticiasFiltrosActivity::class.java))
                }

                R.id.it_perfil -> {
                    startActivity(Intent(this@OpcionesUsuarioActivity,OpcionesUsuarioActivity::class.java))

                }

                R.id.it_cerrar_sesion -> {
                    logOut()
                }

            }

            true
        }


///////////////////


        binding.btnEliminar.setOnClickListener {
            registroViewModel.deleteUserWithEmailAndPassword(
                binding.m3TietEmail.text.toString(),
                binding.m3TietContrasenia.text.toString(),
                binding.m3TietUser.text.toString()
            )
            logOut()
        }

    }

    private fun getUserData() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid

            Log.d("TAG", email.toString())
            Log.d("TAG", uid.toString())


        }
    }
    private fun logOut() {

        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }



}