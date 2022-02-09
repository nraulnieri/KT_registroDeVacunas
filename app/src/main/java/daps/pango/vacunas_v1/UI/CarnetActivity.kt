package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.CarnetDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.CarnetService
import daps.pango.vacunas_v1.databinding.ActivityCarnetBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarnetActivity : AppCompatActivity() {
    lateinit var binding: ActivityCarnetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarnetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveCarnet.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchCarnet.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditCarnet.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteCarnet.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {
        //val id_carnet = binding.etIdCarnet.text.toString().toInt()
        val id_persona = binding.etCarnetNomPersona.text.toString().trim().toInt()
        val id_aplicador = binding.etCarnetNomMedico.text.toString().trim().toInt()
        val id_usuario = binding.etCarnetUsuario.text.toString().trim().toInt()
        val fec_emision_carnet = binding.etCarnetFechaEmision.text.toString().trim()


        val registerData = CarnetDataCollectionItem(
            id_carnet = null,
            id_persona = id_persona,
            id_aplicador = id_aplicador,
            id_usuario = id_usuario,
            fec_emision_carnet = fec_emision_carnet
        )

        addRegister(registerData) {
            if (it?.id_carnet != null) {
                Toast.makeText(
                    this@CarnetActivity,
                    "Registro Realizado: " + it.id_carnet,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@CarnetActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: CarnetDataCollectionItem,
        onResult: (CarnetDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(CarnetService::class.java)
        var result: Call<CarnetDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<CarnetDataCollectionItem> {
            override fun onFailure(call: Call<CarnetDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<CarnetDataCollectionItem>,
                response: retrofit2.Response<CarnetDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@CarnetActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@CarnetActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdCarnet.text.toString().toInt()
        val personService: CarnetService =
            RestEngine.buildService().create(CarnetService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@CarnetActivity,
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
                        this@CarnetActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@CarnetActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@CarnetActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_carnet = binding.etIdCarnet.text.toString().toInt()
        val id_persona = binding.etCarnetNomPersona.text.toString().trim().toInt()
        val id_aplicador = binding.etCarnetNomMedico.text.toString().trim().toInt()
        val id_usuario = binding.etCarnetUsuario.text.toString().trim().toInt()
        val fec_emision_carnet = binding.etCarnetFechaEmision.text.toString().trim()


        val registerData = CarnetDataCollectionItem(
            id_carnet = id_carnet,
            id_persona = id_persona,
            id_aplicador = id_aplicador,
            id_usuario = id_usuario,
            fec_emision_carnet = fec_emision_carnet
        )
        val retrofit = RestEngine.buildService().create(CarnetService::class.java)
        var result: Call<CarnetDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<CarnetDataCollectionItem> {
            override fun onFailure(call: Call<CarnetDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@CarnetActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<CarnetDataCollectionItem>,
                response: Response<CarnetDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@CarnetActivity,
                        "Registro Actualizado: " + response.body()!!.id_carnet,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@CarnetActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@CarnetActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdCarnet.text.toString().toInt()
        val registerService: CarnetService =
            RestEngine.buildService().create(CarnetService::class.java)
        var result: Call<CarnetDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<CarnetDataCollectionItem> {
            override fun onFailure(call: Call<CarnetDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@CarnetActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<CarnetDataCollectionItem>,
                response: retrofit2.Response<CarnetDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CarnetActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdCarnet.setText("" + response.body()!!.id_carnet)
                        etCarnetNomPersona.setText("" + response.body()!!.id_persona)
                        etCarnetNomMedico.setText("" + response.body()!!.id_aplicador)
                        etCarnetUsuario.setText("" + response.body()!!.id_usuario)
                        etCarnetFechaEmision.setText("" + response.body()!!.fec_emision_carnet)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@CarnetActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@CarnetActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdCarnet.setText("")
            etCarnetNomPersona.setText("")
            etCarnetNomMedico.setText("")
            etCarnetUsuario.setText("")
            etCarnetFechaEmision.setText("")
        }
    }
}
