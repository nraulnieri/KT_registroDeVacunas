package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.AplicadorDataCollectionItem
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.AplicadorService
import daps.pango.vacunas_v1.databinding.ActivityAplicadorBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AplicadorActivity : AppCompatActivity() {
    lateinit var binding: ActivityAplicadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAplicadorBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnSaveAplicador.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchAplicador.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditAplicador.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteAplicador.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {
        //val id_aplicador = binding.etIdAplicador.text.toString().toInt()


        val nombre_aplicador = binding.etNombreAplicador.text.toString().trim()
        val id_especialidad = binding.etAplicadorIdEspecialidad.text.toString().trim().toInt()
        val id_centro_vacunacion = binding.etAplicadorIdEspecialidad.text.toString().trim().toInt()


        val registerData = AplicadorDataCollectionItem(
            id_aplicador = null,
            nombre_aplicador = nombre_aplicador,
            id_especialidad = id_especialidad,
            id_centro_vacunacion = id_centro_vacunacion
        )

        addRegister(registerData) {
            if (it?.id_aplicador != null) {
                Toast.makeText(
                    this@AplicadorActivity,
                    "Registro Realizado: " + it.id_aplicador,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@AplicadorActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: AplicadorDataCollectionItem,
        onResult: (AplicadorDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(AplicadorService::class.java)
        var result: Call<AplicadorDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<AplicadorDataCollectionItem> {
            override fun onFailure(call: Call<AplicadorDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<AplicadorDataCollectionItem>,
                response: retrofit2.Response<AplicadorDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@AplicadorActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@AplicadorActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdAplicador.text.toString().toInt()
        val personService: AplicadorService =
            RestEngine.buildService().create(AplicadorService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@AplicadorActivity,
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
                        this@AplicadorActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@AplicadorActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@AplicadorActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_aplicador = binding.etIdAplicador.text.toString().toInt()
        val nombre_aplicador = binding.etNombreAplicador.text.toString().trim()
        val id_especialidad = binding.etAplicadorIdEspecialidad.text.toString().trim().toInt()
        val id_centro_vacunacion = binding.etAplicadorIdEspecialidad.text.toString().trim().toInt()


        val registerData = AplicadorDataCollectionItem(
            id_aplicador = id_aplicador,
            nombre_aplicador = nombre_aplicador,
            id_especialidad = id_especialidad,
            id_centro_vacunacion = id_centro_vacunacion
        )
        val retrofit = RestEngine.buildService().create(AplicadorService::class.java)
        var result: Call<AplicadorDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<AplicadorDataCollectionItem> {
            override fun onFailure(call: Call<AplicadorDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@AplicadorActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<AplicadorDataCollectionItem>,
                response: Response<AplicadorDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@AplicadorActivity,
                        "Registro Actualizado: " + response.body()!!.id_aplicador,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@AplicadorActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@AplicadorActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdAplicador.text.toString().toInt()
        val registerService: AplicadorService =
            RestEngine.buildService().create(AplicadorService::class.java)
        var result: Call<AplicadorDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<AplicadorDataCollectionItem> {
            override fun onFailure(call: Call<AplicadorDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@AplicadorActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<AplicadorDataCollectionItem>,
                response: retrofit2.Response<AplicadorDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@AplicadorActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etNombreAplicador.setText("" + response.body()!!.nombre_aplicador)
                        etAplicadorIdEspecialidad.setText("" + response.body()!!.id_especialidad)
                        etAplicadorIdCentroVacunacion.setText("" + response.body()!!.id_centro_vacunacion)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@AplicadorActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@AplicadorActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdAplicador.setText("")
            etNombreAplicador.setText("")
            etAplicadorIdCentroVacunacion.setText("")
            etAplicadorIdEspecialidad.setText("")
        }
    }
}