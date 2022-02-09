package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.UbicacionDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.UbicacionService
import daps.pango.vacunas_v1.databinding.ActivityUbicacionBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UbicacionActivity : AppCompatActivity() {
    lateinit var binding:ActivityUbicacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityUbicacionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSaveUbicacion.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchUbicacion.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditUbicacion.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteUbicacion.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val id_ubicacion= binding.etIdUbicacion.text.toString().trim().toInt()
        val nombre_ubicacion= binding.etUbicacion.text.toString().trim()



        val registerData = UbicacionDataCollectionItem(
            id_ubicacion = null,
            nombre_ubicacion = nombre_ubicacion
        )

        addRegister(registerData) {
            if (it?.id_ubicacion != null) {
                Toast.makeText(
                    this@UbicacionActivity,
                    "Registro Realizado: " + it.id_ubicacion,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@UbicacionActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: UbicacionDataCollectionItem,
        onResult: (UbicacionDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(UbicacionService::class.java)
        var result: Call<UbicacionDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<UbicacionDataCollectionItem> {
            override fun onFailure(call: Call<UbicacionDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<UbicacionDataCollectionItem>,
                response: retrofit2.Response<UbicacionDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@UbicacionActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@UbicacionActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdUbicacion.text.toString().toInt()
        val personService: UbicacionService =
            RestEngine.buildService().create(UbicacionService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@UbicacionActivity,
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
                        this@UbicacionActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@UbicacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@UbicacionActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_ubicacion= binding.etIdUbicacion.text.toString().trim().toInt()
        val nombre_ubicacion= binding.etUbicacion.text.toString().trim()



        val registerData = UbicacionDataCollectionItem(
            id_ubicacion = id_ubicacion,
            nombre_ubicacion = nombre_ubicacion
        )
        val retrofit = RestEngine.buildService().create(UbicacionService::class.java)
        var result: Call<UbicacionDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<UbicacionDataCollectionItem> {
            override fun onFailure(call: Call<UbicacionDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@UbicacionActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<UbicacionDataCollectionItem>,
                response: Response<UbicacionDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@UbicacionActivity,
                        "Registro Actualizado: " + response.body()!!.id_ubicacion,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@UbicacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@UbicacionActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdUbicacion.text.toString().toInt()
        val registerService: UbicacionService =
            RestEngine.buildService().create(UbicacionService::class.java)
        var result: Call<UbicacionDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<UbicacionDataCollectionItem> {
            override fun onFailure(call: Call<UbicacionDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@UbicacionActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UbicacionDataCollectionItem>,
                response: retrofit2.Response<UbicacionDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@UbicacionActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdUbicacion.setText("" + response.body()!!.id_ubicacion)
                        etUbicacion.setText("" + response.body()!!.nombre_ubicacion)



                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@UbicacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@UbicacionActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdUbicacion.setText("")
            etUbicacion.setText("")
        }
    }
}