package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.OcupacionDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.OcupacionService
import daps.pango.vacunas_v1.databinding.ActivityOcupacionBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OcupacionActivity : AppCompatActivity() {
    lateinit var binding:ActivityOcupacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityOcupacionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSaveOcupacion.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchOcupacion.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditOcupacion.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteOcupacion.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val id_ocupacion= binding.etIdOcupacion.text.toString().trim().toInt()
        val nombre_ocupacion= binding.etOcupacion.text.toString().trim()



        val registerData = OcupacionDataCollectionItem(
            id_ocupacion = null,
            nombre_ocupacion = nombre_ocupacion
        )

        addRegister(registerData) {
            if (it?.id_ocupacion     != null) {
                Toast.makeText(
                    this@OcupacionActivity,
                    "Registro Realizado: " + it.id_ocupacion,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@OcupacionActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: OcupacionDataCollectionItem,
        onResult: (OcupacionDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(OcupacionService::class.java)
        var result: Call<OcupacionDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<OcupacionDataCollectionItem> {
            override fun onFailure(call: Call<OcupacionDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<OcupacionDataCollectionItem>,
                response: retrofit2.Response<OcupacionDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@OcupacionActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@OcupacionActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdOcupacion.text.toString().toInt()
        val personService: OcupacionService =
            RestEngine.buildService().create(OcupacionService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@OcupacionActivity,
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
                        this@OcupacionActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@OcupacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@OcupacionActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_ocupacion= binding.etIdOcupacion.text.toString().trim().toInt()
        val nombre_ocupacion= binding.etOcupacion.text.toString().trim()


        val registerData = OcupacionDataCollectionItem(
            id_ocupacion = id_ocupacion,
            nombre_ocupacion = nombre_ocupacion
        )
        val retrofit = RestEngine.buildService().create(OcupacionService::class.java)
        var result: Call<OcupacionDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<OcupacionDataCollectionItem> {
            override fun onFailure(call: Call<OcupacionDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@OcupacionActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<OcupacionDataCollectionItem>,
                response: Response<OcupacionDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@OcupacionActivity,
                        "Registro Actualizado: " + response.body()!!.id_ocupacion,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@OcupacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@OcupacionActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdOcupacion.text.toString().toInt()
        val registerService: OcupacionService =
            RestEngine.buildService().create(OcupacionService::class.java)
        var result: Call<OcupacionDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<OcupacionDataCollectionItem> {
            override fun onFailure(call: Call<OcupacionDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@OcupacionActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<OcupacionDataCollectionItem>,
                response: retrofit2.Response<OcupacionDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@OcupacionActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdOcupacion.setText("" + response.body()!!.id_ocupacion)
                        etOcupacion.setText("" + response.body()!!.nombre_ocupacion)



                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@OcupacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@OcupacionActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdOcupacion.setText("")
            etOcupacion.setText("")
        }
    }
}