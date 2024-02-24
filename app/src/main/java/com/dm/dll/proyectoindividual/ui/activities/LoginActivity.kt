package com.dm.dll.proyectoindividual.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.dm.dll.proyectoindividual.databinding.ActivityLoginBinding
import com.dm.dll.proyectoindividual.ui.viewmodels.LoginViewModel


import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor


class LoginActivity : AppCompatActivity() {


    private lateinit var binding:ActivityLoginBinding

    //private lateinit var btnFinger:ImageView
    //private lateinit var txtInfo:TextView

    private lateinit var executor: Executor //permite ejecutar app en segundo plano
        //necesita hilos
    private lateinit var biometricPrompt: BiometricPrompt   // maja los eventos del biometrico
    private lateinit var promptInfo: BiometricPrompt.PromptInfo //es el dialogo, lo que se meustra


    private val loginViewModel: LoginViewModel by viewModels() //esto una inyecciond e dependencias
        //este delegado by me da ya todo simple.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_login)
        setContentView(binding.root)

        // Initialize Firebase Auth



        initListener()
        initObservables()
        AutenticationVariables()
        loginViewModel.checkBiometric(this)






    }

    override fun onStart() {
        super.onStart()
        //aca iva el biometrico
    /*
        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.etxtUser.visibility= View.GONE
            binding.etxtPassword.visibility= View.GONE

            binding.imgFinger.visibility=View.VISIBLE
            binding.txtInfo.text=getString(R.string.biometric_succes)

            startActivity(Intent(this,MainActivity::class.java))
        }else{

            binding.imgFinger.visibility= View.GONE
            binding.txtInfo.text=getString(R.string.no_user)
        }
*/
    }

    private fun initListener(){

        binding.imgFinger
        binding.imgFinger.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)

        }

        binding.btnSaveUser.setOnClickListener {
            /*
            loginViewModel.createUserWithEmailAndPassword(
                binding.m3TietEmail.text.toString(),
                binding.m3TietContrasenia.text.toString()
            )
            */
            startActivity(Intent(this@LoginActivity,RegistroActivity::class.java))

        }
        binding.btnSignUser.setOnClickListener {

            loginViewModel.sigInUserWithEmailAndPassword(
                binding.m3TietEmail.text.toString(),
                binding.m3TietContrasenia.text.toString()
            )
        }

        //binding.btnNobels.setOnClickListener {
            //iniciarNobeles()
        //}
    }




/*
    private fun iniciarNobeles() {
        try {
            startActivity(Intent(this@LoginActivity,NobelActivity::class.java))
        }catch (e:Exception){
            Log.w("TAG", "salto_a_nobeles:failure")
            Snackbar.make(this,binding.etxtUser,"salto_a_nobeles:failure",Snackbar.LENGTH_LONG).show()
        };

    }
    */

    private fun AutenticationVariables(){
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@LoginActivity,InicioActivity::class.java))
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL)
            .build()

    }

    private fun initObservables(){

        loginViewModel.user.observe(this){
            startActivity(Intent(this,InicioActivity::class.java))//esta es la vista que me sale a crear cuenta?
        }

        loginViewModel.error.observe(this){
            Snackbar.make(
                this,
                binding.edtText,
                it,
                Snackbar.LENGTH_LONG
            ).show()


        }

        loginViewModel.resultCheckBiometric.observe(this){

        }
        /*
        loginViewModel.resultCheckBiometric.observe(this){
            when(it){
                BiometricManager.BIOMETRIC_SUCCESS->{
                    //binding.imgFinger.visibility= View.VISIBLE
                    binding.txtInfo.text=getString( R.string.biometric_succes)
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                    binding.txtInfo.text=getString( R.string.biometric_no_harware)
                    Log.e("MY_APP_TAG", "No biometric features available on this device.")
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                    binding.txtInfo.text=getString( R.string.biometric_no_harware)+"_HW"
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    }
                    startActivityForResult(enrollIntent, 100)
                    //luego de configurar aparece en blanco porque no tiene un retorno (vista)
                }
            }
        }

        */
    }






}