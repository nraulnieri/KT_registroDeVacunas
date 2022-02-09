package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.DetCarnetDataCollectionItem
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.DetCarnetService
import daps.pango.vacunas_v1.databinding.ActivityDetcarnetBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetCarnetActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetcarnetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetcarnetBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.btnSaveDetCarnet.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchDetCarnet.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditDetCarnet.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteDetCarnet.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {

        //val id_detcarnet = binding.etIdDetCarnet.text.toString().toInt()

        val id_carnet=binding.etDetCarnetIdCarnet.text.toString().trim().toInt()
        val id_medico = binding.etDetCarnetIdAplicador.text.toString().trim().toInt()
        val id_laboratorio=binding.etDetCarnetIdLaboratorio.text.toString().trim().toInt()
        val num_lote= binding.etDetCarnetNumLote.text.toString().trim().toInt()



        val registerData = DetCarnetDataCollectionItem(
            id_detcarnet = null,
            id_carnet = id_carnet,
            id_medico = id_medico,
            id_laboratorio = id_laboratorio,
            num_lote = num_lote
        )

        addRegister(registerData) {
            if (it?.id_detcarnet != null) {
                Toast.makeText(
                    this@DetCarnetActivity,
                    "Registro Realizado: " + it.id_detcarnet,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@DetCarnetActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: DetCarnetDataCollectionItem,
        onResult: (DetCarnetDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(DetCarnetService::class.java)
        var result: Call<DetCarnetDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<DetCarnetDataCollectionItem> {
            override fun onFailure(call: Call<DetCarnetDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<DetCarnetDataCollectionItem>,
                response: retrofit2.Response<DetCarnetDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@DetCarnetActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@DetCarnetActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdDetCarnet.text.toString().toInt()
        val personService: DetCarnetService =
            RestEngine.buildService().create(DetCarnetService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@DetCarnetActivity,
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
                        this@DetCarnetActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@DetCarnetActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@DetCarnetActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {

        val id_detcarnet = binding.etIdDetCarnet.text.toString().toInt()

        val id_carnet=binding.etDetCarnetIdCarnet.text.toString().trim().toInt()
        val id_medico = binding.etDetCarnetIdAplicador.text.toString().trim().toInt()
        val id_laboratorio=binding.etDetCarnetIdLaboratorio.text.toString().trim().toInt()
        val num_lote= binding.etDetCarnetNumLote.text.toString().trim().toInt()


        val registerData = DetCarnetDataCollectionItem(
            id_detcarnet = id_detcarnet,
            id_carnet = id_carnet,
            id_medico = id_medico,
            id_laboratorio = id_laboratorio,
            num_lote=num_lote
        )
        val retrofit = RestEngine.buildService().create(DetCarnetService::class.java)
        var result: Call<DetCarnetDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<DetCarnetDataCollectionItem> {
            override fun onFailure(call: Call<DetCarnetDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@DetCarnetActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<DetCarnetDataCollectionItem>,
                response: Response<DetCarnetDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@DetCarnetActivity,
                        "Registro Actualizado: " + response.body()!!.id_detcarnet,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@DetCarnetActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@DetCarnetActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdDetCarnet.text.toString().toInt()
        val registerService: DetCarnetService =
            RestEngine.buildService().create(DetCarnetService::class.java)
        var result: Call<DetCarnetDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<DetCarnetDataCollectionItem> {
            override fun onFailure(call: Call<DetCarnetDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@DetCarnetActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetCarnetDataCollectionItem>,
                response: retrofit2.Response<DetCarnetDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@DetCarnetActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etDetCarnetIdCarnet.setText("" + response.body()!!.id_carnet)
                        etDetCarnetIdAplicador.setText("" + response.body()!!.id_medico)
                        etDetCarnetIdLaboratorio.setText("" + response.body()!!.id_laboratorio)
                        etDetCarnetNumLote.setText("" + response.body()!!.num_lote)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@DetCarnetActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@DetCarnetActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etDetCarnetIdCarnet.setText("" )
            etDetCarnetIdAplicador.setText("" )
            etDetCarnetIdLaboratorio.setText("")
            etDetCarnetNumLote.setText("")
        }
    }
}
