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
import com.dm.dll.proyectoindividual.databinding.ActivityInicioBinding
import com.dm.dll.proyectoindividual.ui.adapters.NewsAdapter
import com.dm.dll.proyectoindividual.ui.viewmodels.InicioViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InicioActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityInicioBinding

    private val adapter =NewsAdapter()
    private val viewModel:InicioViewModel by viewModels()
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = Firebase.auth
        getUserData()



        ///////////

        initVariables("")
        initRecyclerView()
        initObservers()
        initListeners()
        swipeRecyclerView()

        viewModel.getAllNews()

    }

    private fun initObservers() {

        viewModel.listItems.observe(this) { //Este es el observable
            binding.animationView.visibility = View.VISIBLE
            adapter.submitList(it)
            binding.animationView.visibility = View.GONE
        }

        viewModel.error.observe(this) {
            adapter.submitList(emptyList())
            initVariables(it)
            dialog.show()
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

    private fun initListeners() {

        binding.imgMenu.setOnClickListener {
            binding.inicioActivityConMenuLateral.openDrawer(GravityCompat.START)
        }

        binding.menuPrincipalNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.it_home -> {

                    startActivity(Intent(this@InicioActivity,InicioActivity::class.java))

                }

                R.id.it_titulares -> {
                    startActivity(Intent(this@InicioActivity,TitularesActivity::class.java))

                }

                R.id.it_buscar -> {
                    startActivity(Intent(this@InicioActivity,FormularioNoticiasFiltrosActivity::class.java))

                }

                R.id.it_perfil -> {
                    startActivity(Intent(this@InicioActivity,OpcionesUsuarioActivity::class.java))
                }

                R.id.it_cerrar_sesion -> {
                    logOut()
                }

            }

            true
        }


///////////////////

        binding.swiperv.setOnRefreshListener {
            viewModel.getAllNews()
            binding.swiperv.isRefreshing = false
        }

        binding.edtFiltro.addTextChangedListener {filtro->
            //Log.d("TAG",filtro.toString())
            val listFilter= adapter.currentList.toList().filter {item->
                item.title.contains(filtro.toString())!!
            }
            adapter.submitList(listFilter)

            if(filtro.isNullOrBlank()){
                viewModel.getAllNews()
            }
        }

    }

    private fun swipeRecyclerView() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                //Log.d("TAG","Functio swipe")
                val position= viewHolder.adapterPosition
                val list= adapter.currentList.toMutableList()
                list.removeAt(position)
                adapter.submitList(list)
            }
        }).attachToRecyclerView(binding.rvUsers)
    }
    private fun initRecyclerView() {
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager =
            LinearLayoutManager(
                this@InicioActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
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