package com.dm.dll.proyectoindividual.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dm.dll.proyectoindividual.R
import com.dm.dll.proyectoindividual.core.Constants
import com.dm.dll.proyectoindividual.databinding.ActivityFormularioNoticiasFiltrosBinding
import com.dm.dll.proyectoindividual.databinding.ActivityInicioBinding
import com.dm.dll.proyectoindividual.databinding.ActivityNoticiasFiltradasBinding
import com.dm.dll.proyectoindividual.databinding.ActivityRegistroBinding
import com.dm.dll.proyectoindividual.ui.adapters.NewsAdapter
import com.dm.dll.proyectoindividual.ui.viewmodels.FormularioNoticiasFiltradasViewModel
import com.dm.dll.proyectoindividual.ui.viewmodels.InicioViewModel
import com.dm.dll.proyectoindividual.ui.viewmodels.NoticiasFiltradasViewModel
import com.dm.dll.proyectoindividual.ui.viewmodels.RegistroViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class FormularioNoticiasFiltrosActivity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth
    private lateinit var binding: ActivityFormularioNoticiasFiltrosBinding
    private lateinit var executor: Executor
    private val registroViewModel: FormularioNoticiasFiltradasViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioNoticiasFiltrosBinding.inflate(layoutInflater)
        setContentView(binding.root)



        auth = Firebase.auth
        getUserData()


        initListeners()
        initObservables()

    }

    private fun initObservables() {
        registroViewModel.filtros.observe(this){
            val intent=Intent(this,NoticiasFiltradasActivity::class.java)
            intent.putExtra(Constants.DATOS_FILTRO, arrayListOf(
                binding.m3TietTema.text.toString(),
                binding.m3TietOrden.text.toString(),
                binding.m3TietLimite.text.toString())
            )
            startActivity(intent)//esta es la vista que me sale a crear cuenta?
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

    private fun initListeners(){//en este caso no haria nada
        binding.btnConsultar.setOnClickListener {
            registroViewModel.createCadenaStrings(
                binding.m3TietTema.text.toString(),
                binding.m3TietOrden.text.toString(),
                binding.m3TietLimite.text.toString()
            )

        }

        binding.imgMenu.setOnClickListener {
            binding.inicioActivityConMenuLateral.openDrawer(GravityCompat.START)
        }

        binding.menuPrincipalNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.it_home -> {

                    startActivity(Intent(this@FormularioNoticiasFiltrosActivity,InicioActivity::class.java))

                }

                R.id.it_titulares -> {
                    startActivity(Intent(this@FormularioNoticiasFiltrosActivity,TitularesActivity::class.java))

                }

                R.id.it_buscar -> {
                    startActivity(Intent(this@FormularioNoticiasFiltrosActivity,FormularioNoticiasFiltrosActivity::class.java))
                }

                R.id.it_perfil -> {

                }

                R.id.it_cerrar_sesion -> {
                    logOut()
                }

            }

            true
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