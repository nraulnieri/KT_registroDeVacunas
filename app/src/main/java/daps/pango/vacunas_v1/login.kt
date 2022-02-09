package daps.pango.vacunas_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import daps.pango.vacunas_v1.UI.HomeActivity
import daps.pango.vacunas_v1.databinding.ActivityLoginBinding

class login : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if(binding.etUsuario.text.toString() =="admin" && binding.etContrasena.text.toString()=="123456"){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Usuario o Contraseña es Invalido",Toast.LENGTH_SHORT).show()
            }
        }
    }
}