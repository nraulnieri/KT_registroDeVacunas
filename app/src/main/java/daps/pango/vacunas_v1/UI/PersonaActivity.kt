package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.PersonaDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.PersonaService
import daps.pango.vacunas_v1.databinding.ActivityPersonaBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.time.LocalDate

class PersonaActivity : AppCompatActivity() {
    lateinit var binding:ActivityPersonaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityPersonaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSavePersona.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchPersona.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditPersona.setOnClickListener {
            callServicePut()
        }

        binding.btnDeletePersona.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val id_persona= binding.etIdPersona.text.toString().trim(),
        val dni_persona= binding.etDocuPersona.text.toString().trim().toBigInteger()
        val nombre_persona= binding.etNombresPersona.text.toString().trim()
        val fec_nac_persona= binding.etFecNacimiento.text.toString().trim()
        val genero= binding.etGeneroPersona.text.toString().trim()
        val id_ocupacion= binding.etPersonaIdOcupacion.text.toString().trim().toInt()
        val obs_ocupacion_persona= binding.etPersonaOcupacion.text.toString().trim()
        val id_ubicacion= binding.etPersonaIdUbicacion.text.toString().trim().toInt()
        val obs_ubicacion_persona= binding.etPersonaUbicacion.text.toString().trim()



        val registerData = PersonaDataCollectionItem(
            id_persona= null,
            dni_persona= dni_persona,
            nombre_persona= nombre_persona,
            fec_nac_persona= fec_nac_persona,
            genero= genero,
            id_ocupacion= id_ocupacion,
            obs_ocupacion_persona= obs_ocupacion_persona,
            id_ubicacion= id_ubicacion,
            obs_ubicacion_persona= obs_ubicacion_persona
        )

        addRegister(registerData) {
            if (it?.id_persona != null) {
                Toast.makeText(
                    this@PersonaActivity,
                    "Registro Realizado: " + it.id_persona,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@PersonaActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: PersonaDataCollectionItem,
        onResult: (PersonaDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(PersonaService::class.java)
        var result: Call<PersonaDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<PersonaDataCollectionItem> {
            override fun onFailure(call: Call<PersonaDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<PersonaDataCollectionItem>,
                response: retrofit2.Response<PersonaDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@PersonaActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@PersonaActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdPersona.text.toString().toInt()
        val personService: PersonaService =
            RestEngine.buildService().create(PersonaService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@PersonaActivity,
                    "Error al Eliminar Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@PersonaActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@PersonaActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@PersonaActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_persona= binding.etIdPersona.text.toString().trim().toInt()
        val dni_persona= binding.etDocuPersona.text.toString().trim().toBigInteger()
        val nombre_persona= binding.etNombresPersona.text.toString().trim()
        val fec_nac_persona= binding.etFecNacimiento.text.toString().trim()
        val genero= binding.etGeneroPersona.text.toString().trim()
        val id_ocupacion= binding.etPersonaIdOcupacion.text.toString().trim().toInt()
        val obs_ocupacion_persona= binding.etPersonaOcupacion.text.toString().trim()
        val id_ubicacion= binding.etPersonaIdUbicacion.text.toString().trim().toInt()
        val obs_ubicacion_persona= binding.etPersonaUbicacion.text.toString().trim()



        val registerData = PersonaDataCollectionItem(
            id_persona= id_persona,
            dni_persona= dni_persona,
            nombre_persona= nombre_persona,
            fec_nac_persona= fec_nac_persona,
            genero= genero,
            id_ocupacion= id_ocupacion,
            obs_ocupacion_persona= obs_ocupacion_persona,
            id_ubicacion= id_ubicacion,
            obs_ubicacion_persona= obs_ubicacion_persona
        )
        val retrofit = RestEngine.buildService().create(PersonaService::class.java)
        var result: Call<PersonaDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<PersonaDataCollectionItem> {
            override fun onFailure(call: Call<PersonaDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@PersonaActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<PersonaDataCollectionItem>,
                response: Response<PersonaDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@PersonaActivity,
                        "Registro Actualizado: " + response.body()!!.id_persona,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@PersonaActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@PersonaActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdPersona.text.toString().toInt()
        val registerService: PersonaService =
            RestEngine.buildService().create(PersonaService::class.java)
        var result: Call<PersonaDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<PersonaDataCollectionItem> {
            override fun onFailure(call: Call<PersonaDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@PersonaActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<PersonaDataCollectionItem>,
                response: retrofit2.Response<PersonaDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@PersonaActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdPersona.setText("" + response.body()!!.id_persona)
                        etDocuPersona.setText("" + response.body()!!.dni_persona)
                        etNombresPersona.setText("" + response.body()!!.nombre_persona)
                        etFecNacimiento.setText("" + response.body()!!.fec_nac_persona)
                        etGeneroPersona.setText("" + response.body()!!.genero)
                        etPersonaIdOcupacion.setText("" + response.body()!!.id_ocupacion)
                        etPersonaOcupacion.setText("" + response.body()!!.obs_ocupacion_persona)
                        etPersonaIdUbicacion.setText("" + response.body()!!.id_ubicacion)
                        etPersonaUbicacion.setText("" + response.body()!!.obs_ubicacion_persona)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@PersonaActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@PersonaActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdPersona.setText("" )
            etDocuPersona.setText("" )
            etNombresPersona.setText("" )
            etFecNacimiento.setText("" )
            etGeneroPersona.setText("" )
            etPersonaIdOcupacion.setText("" )
            etPersonaOcupacion.setText("" )
            etPersonaIdUbicacion.setText("" )
            etPersonaUbicacion.setText("" )
        }
    }
}