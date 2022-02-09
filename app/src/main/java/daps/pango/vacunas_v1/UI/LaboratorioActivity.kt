package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.LaboratorioDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.LaboratorioService
import daps.pango.vacunas_v1.databinding.ActivityLaboratorioBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaboratorioActivity : AppCompatActivity() {
    lateinit var binding:ActivityLaboratorioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityLaboratorioBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSaveLaboratorio.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchLaboratorio.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditLaboratorio.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteLaboratorio.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val id_laboratorio= binding.etIdLaboratorio.text.toString().trim().toInt()
        val nombre_laboratorio= binding.etNombreLaboratorio.text.toString().trim()
        val direccion_laboratorio= binding.etDirLaboratorio.text.toString().trim()
        val id_ubicacion= binding.etLaboratorioIdUbicacion.text.toString().trim().toInt()




        val registerData = LaboratorioDataCollectionItem(
            id_laboratorio = null,
            nombre_laboratorio = nombre_laboratorio,
            direccion_laboratorio = direccion_laboratorio,
            id_ubicacion = id_ubicacion
        )

        addRegister(registerData) {
            if (it?.id_laboratorio != null) {
                Toast.makeText(
                    this@LaboratorioActivity,
                    "Registro Realizado: " + it.id_laboratorio,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@LaboratorioActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: LaboratorioDataCollectionItem,
        onResult: (LaboratorioDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(LaboratorioService::class.java)
        var result: Call<LaboratorioDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<LaboratorioDataCollectionItem> {
            override fun onFailure(call: Call<LaboratorioDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<LaboratorioDataCollectionItem>,
                response: retrofit2.Response<LaboratorioDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@LaboratorioActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@LaboratorioActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdLaboratorio.text.toString().toInt()
        val personService: LaboratorioService =
            RestEngine.buildService().create(LaboratorioService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@LaboratorioActivity,
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
                        this@LaboratorioActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@LaboratorioActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@LaboratorioActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_laboratorio= binding.etIdLaboratorio.text.toString().trim().toInt()
        val nombre_laboratorio= binding.etNombreLaboratorio.text.toString().trim()
        val direccion_laboratorio= binding.etDirLaboratorio.text.toString().trim()
        val id_ubicacion= binding.etLaboratorioIdUbicacion.text.toString().trim().toInt()


        val registerData = LaboratorioDataCollectionItem(
            id_laboratorio = id_laboratorio,
            nombre_laboratorio = nombre_laboratorio,
            direccion_laboratorio = direccion_laboratorio,
            id_ubicacion = id_ubicacion
        )
        val retrofit = RestEngine.buildService().create(LaboratorioService::class.java)
        var result: Call<LaboratorioDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<LaboratorioDataCollectionItem> {
            override fun onFailure(call: Call<LaboratorioDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@LaboratorioActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<LaboratorioDataCollectionItem>,
                response: Response<LaboratorioDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@LaboratorioActivity,
                        "Registro Actualizado: " + response.body()!!.id_laboratorio,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@LaboratorioActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@LaboratorioActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdLaboratorio.text.toString().toInt()
        val registerService: LaboratorioService =
            RestEngine.buildService().create(LaboratorioService::class.java)
        var result: Call<LaboratorioDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<LaboratorioDataCollectionItem> {
            override fun onFailure(call: Call<LaboratorioDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@LaboratorioActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LaboratorioDataCollectionItem>,
                response: retrofit2.Response<LaboratorioDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@LaboratorioActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdLaboratorio.setText("" + response.body()!!.id_laboratorio)
                        etNombreLaboratorio.setText("" + response.body()!!.nombre_laboratorio)
                        etLaboratorioIdUbicacion.setText("" + response.body()!!.id_ubicacion)
                        etDirLaboratorio.setText("" + response.body()!!.direccion_laboratorio)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@LaboratorioActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@LaboratorioActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdLaboratorio.setText("")
            etNombreLaboratorio.setText("")
            etLaboratorioIdUbicacion.setText("")
            etDirLaboratorio.setText("")
        }
    }
}