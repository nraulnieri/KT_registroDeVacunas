package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.DetIngresoDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.DetIngresoService
import daps.pango.vacunas_v1.databinding.ActivityDetingresoBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetingresoActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetingresoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetingresoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSaveDetIngreso.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchDetIngreso.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditDetIngreso.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteDetIngreso.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {
        //val id_det_ingreso = binding.etIdDetIngreso.text.toString().toInt()


        val id_cabecera_ingreso = binding.etCabeceraIngreso.text.toString().trim().toInt()
        val num_lote = binding.etNumeroLote.text.toString().trim().toInt()


        val registerData = DetIngresoDataCollectionItem(
            id_det_ingreso = null,
            id_cabecera_ingreso = id_cabecera_ingreso,
            num_lote = num_lote,

            )

        addRegister(registerData) {
            if (it?.id_det_ingreso != null) {
                Toast.makeText(
                    this@DetingresoActivity,
                    "Registro Realizado: " + it.id_det_ingreso,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@DetingresoActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: DetIngresoDataCollectionItem,
        onResult: (DetIngresoDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(DetIngresoService::class.java)
        var result: Call<DetIngresoDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<DetIngresoDataCollectionItem> {
            override fun onFailure(call: Call<DetIngresoDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<DetIngresoDataCollectionItem>,
                response: retrofit2.Response<DetIngresoDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@DetingresoActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@DetingresoActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdDetIngreso.text.toString().toInt()
        val personService: DetIngresoService =
            RestEngine.buildService().create(DetIngresoService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@DetingresoActivity,
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
                        this@DetingresoActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@DetingresoActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@DetingresoActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_det_ingreso = binding.etIdDetIngreso.text.toString().toInt()


        val id_cabecera_ingreso = binding.etCabeceraIngreso.text.toString().trim().toInt()
        val num_lote = binding.etNumeroLote.text.toString().trim().toInt()


        val registerData = DetIngresoDataCollectionItem(
            id_det_ingreso = id_det_ingreso,
            id_cabecera_ingreso = id_cabecera_ingreso,
            num_lote = num_lote
        )
        val retrofit = RestEngine.buildService().create(DetIngresoService::class.java)
        var result: Call<DetIngresoDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<DetIngresoDataCollectionItem> {
            override fun onFailure(call: Call<DetIngresoDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@DetingresoActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<DetIngresoDataCollectionItem>,
                response: Response<DetIngresoDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@DetingresoActivity,
                        "Registro Actualizado: " + response.body()!!.id_det_ingreso,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@DetingresoActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@DetingresoActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdDetIngreso.text.toString().toInt()
        val registerService: DetIngresoService =
            RestEngine.buildService().create(DetIngresoService::class.java)
        var result: Call<DetIngresoDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<DetIngresoDataCollectionItem> {
            override fun onFailure(call: Call<DetIngresoDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@DetingresoActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetIngresoDataCollectionItem>,
                response: retrofit2.Response<DetIngresoDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@DetingresoActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etCabeceraIngreso.setText("" + response.body()!!.id_cabecera_ingreso)
                        etNumeroLote.setText("" + response.body()!!.num_lote)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@DetingresoActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@DetingresoActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdDetIngreso.setText("")
            etCabeceraIngreso.setText("")
            etNumeroLote.setText("")
        }
    }
}