package daps.pango.vacunas_v1.UI

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import daps.pango.vacunas_v1.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvUsuario.setOnClickListener {
            val intent = Intent(this, UsuarioActivity::class.java)
            startActivity(intent)
        }
        binding.cvLaboratorio.setOnClickListener {
            val intent = Intent(this, LaboratorioActivity::class.java)
            startActivity(intent)
        }
        binding.cvCarnet.setOnClickListener {
            val intent = Intent(this, CarnetActivity::class.java)
            startActivity(intent)
        }
        binding.cvDetalleCarnet.setOnClickListener {
            val intent = Intent(this, DetCarnetActivity::class.java)
            startActivity(intent)
        }
        binding.cvIngreso.setOnClickListener {
            val intent = Intent(this, IngresoActivity::class.java)
            startActivity(intent)
        }
        binding.cvDetalleIngreso.setOnClickListener {
            val intent = Intent(this, DetingresoActivity::class.java)
            startActivity(intent)
        }
        binding.cvAplicador.setOnClickListener {
            val intent = Intent(this, AplicadorActivity::class.java)
            startActivity(intent)
        }
        binding.cvHospital.setOnClickListener {
            val intent = Intent(this, CentroDeVacunacionActivity::class.java)
            startActivity(intent)
        }
        binding.cvOcupacion.setOnClickListener {
            val intent = Intent(this, OcupacionActivity::class.java)
            startActivity(intent)
        }
        binding.cvUbicacion.setOnClickListener {
            val intent = Intent(this, UbicacionActivity::class.java)
            startActivity(intent)
        }
        binding.cvPersona.setOnClickListener {
            val intent = Intent(this, PersonaActivity::class.java)
            startActivity(intent)
        }
        binding.cvVacuna.setOnClickListener {
            val intent = Intent(this, VacunaActivity::class.java)
            startActivity(intent)
        }
        binding.cvLote.setOnClickListener {
            val intent = Intent(this, LoteActivity::class.java)
            startActivity(intent)
        }
        binding.cvEspecialidad.setOnClickListener {
            val intent = Intent(this, EspecialidadActivity::class.java)
            startActivity(intent)
        }
    }
}