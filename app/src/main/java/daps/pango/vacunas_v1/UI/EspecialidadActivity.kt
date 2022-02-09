package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.EspecialidadDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.EspecialidadService
import daps.pango.vacunas_v1.databinding.ActivityEspecialidadBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EspecialidadActivity : AppCompatActivity() {
    lateinit var binding:ActivityEspecialidadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityEspecialidadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSaveEspecialidad.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchEspecialidad.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditEspecialidad.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteEspecialidad.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {

        //val id_especialidad= binding.etIdEspecialidad.text.toString().trim().toInt()
        val nombre_especialidad= binding.etEspecialidad.text.toString().trim()



        val registerData = EspecialidadDataCollectionItem(
            id_especialidad = null,
            nombre_especialidad = nombre_especialidad
        )

        addRegister(registerData) {
            if (it?.id_especialidad != null) {
                Toast.makeText(
                    this@EspecialidadActivity,
                    "Registro Realizado: " + it.id_especialidad,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@EspecialidadActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: EspecialidadDataCollectionItem,
        onResult: (EspecialidadDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(EspecialidadService::class.java)
        var result: Call<EspecialidadDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<EspecialidadDataCollectionItem> {
            override fun onFailure(call: Call<EspecialidadDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<EspecialidadDataCollectionItem>,
                response: retrofit2.Response<EspecialidadDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@EspecialidadActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@EspecialidadActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdEspecialidad.text.toString().toInt()
        val personService: EspecialidadService =
            RestEngine.buildService().create(EspecialidadService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@EspecialidadActivity,
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
                        this@EspecialidadActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@EspecialidadActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@EspecialidadActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_especialidad= binding.etIdEspecialidad.text.toString().trim().toInt()
        val nombre_especialidad= binding.etEspecialidad.text.toString().trim()


        val registerData = EspecialidadDataCollectionItem(
            id_especialidad = id_especialidad,
            nombre_especialidad = nombre_especialidad
        )
        val retrofit = RestEngine.buildService().create(EspecialidadService::class.java)
        var result: Call<EspecialidadDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<EspecialidadDataCollectionItem> {
            override fun onFailure(call: Call<EspecialidadDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@EspecialidadActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<EspecialidadDataCollectionItem>,
                response: Response<EspecialidadDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@EspecialidadActivity,
                        "Registro Actualizado: " + response.body()!!.id_especialidad,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@EspecialidadActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@EspecialidadActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdEspecialidad.text.toString().toInt()
        val registerService: EspecialidadService =
            RestEngine.buildService().create(EspecialidadService::class.java)
        var result: Call<EspecialidadDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<EspecialidadDataCollectionItem> {
            override fun onFailure(call: Call<EspecialidadDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@EspecialidadActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<EspecialidadDataCollectionItem>,
                response: retrofit2.Response<EspecialidadDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EspecialidadActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdEspecialidad.setText("" + response.body()!!.id_especialidad)
                        etEspecialidad.setText("" + response.body()!!.nombre_especialidad)



                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@EspecialidadActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@EspecialidadActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdEspecialidad.setText("")
            etEspecialidad.setText("")
        }
    }
}