package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.IngresoDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.IngresoService
import daps.pango.vacunas_v1.databinding.ActivityIngresoBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresoActivity : AppCompatActivity() {
    lateinit var binding: ActivityIngresoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityIngresoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSaveIngreso.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchIngreso.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditIngreso.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteIngreso.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {

        //val id_ingreso=binding.etIdIngreso.text.toString().trim().toInt()
        val id_laboratorio = binding.etIngresoIdLaboratorio.text.toString().trim().toInt()
        val fec_ingreso = binding.etFechaIngreso.text.toString().trim()
        val obs_ingreso = binding.etObsIngreso.text.toString().trim()


        val registerData = IngresoDataCollectionItem(
            id_ingreso = null,
            id_laboratorio = id_laboratorio,
            fec_ingreso = fec_ingreso,
            obs_ingreso = obs_ingreso
        )

        addRegister(registerData) {
            if (it?.id_ingreso != null) {
                Toast.makeText(
                    this@IngresoActivity,
                    "Registro Realizado: " + it.id_ingreso,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@IngresoActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: IngresoDataCollectionItem,
        onResult: (IngresoDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(IngresoService::class.java)
        var result: Call<IngresoDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<IngresoDataCollectionItem> {
            override fun onFailure(call: Call<IngresoDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<IngresoDataCollectionItem>,
                response: retrofit2.Response<IngresoDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@IngresoActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@IngresoActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdIngreso.text.toString().toInt()
        val personService: IngresoService =
            RestEngine.buildService().create(IngresoService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@IngresoActivity,
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
                        this@IngresoActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@IngresoActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@IngresoActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_ingreso = binding.etIdIngreso.text.toString().trim().toInt()
        val id_laboratorio = binding.etIngresoIdLaboratorio.text.toString().trim().toInt()
        val fec_ingreso = binding.etFechaIngreso.text.toString().trim()
        val obs_ingreso = binding.etObsIngreso.text.toString().trim()


        val registerData = IngresoDataCollectionItem(
            id_ingreso = id_ingreso,
            id_laboratorio = id_laboratorio,
            fec_ingreso = fec_ingreso,
            obs_ingreso = obs_ingreso
        )
        val retrofit = RestEngine.buildService().create(IngresoService::class.java)
        var result: Call<IngresoDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<IngresoDataCollectionItem> {
            override fun onFailure(call: Call<IngresoDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@IngresoActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<IngresoDataCollectionItem>,
                response: Response<IngresoDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@IngresoActivity,
                        "Registro Actualizado: " + response.body()!!.id_ingreso,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@IngresoActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@IngresoActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdIngreso.text.toString().toInt()
        val registerService: IngresoService =
            RestEngine.buildService().create(IngresoService::class.java)
        var result: Call<IngresoDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<IngresoDataCollectionItem> {
            override fun onFailure(call: Call<IngresoDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@IngresoActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<IngresoDataCollectionItem>,
                response: retrofit2.Response<IngresoDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@IngresoActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdIngreso.setText("" + response.body()!!.id_ingreso)
                        etIngresoIdLaboratorio.setText("" + response.body()!!.id_laboratorio)
                        etFechaIngreso.setText("" + response.body()!!.fec_ingreso)
                        etObsIngreso.setText("" + response.body()!!.obs_ingreso)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@IngresoActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@IngresoActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdIngreso.setText("")
            etIngresoIdLaboratorio.setText("")
            etFechaIngreso.setText("")
            etObsIngreso.setText("")
        }
    }
}
