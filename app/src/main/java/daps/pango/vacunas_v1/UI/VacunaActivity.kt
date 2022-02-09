package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.VacunaDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.VacunaService
import daps.pango.vacunas_v1.databinding.ActivityVacunaBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VacunaActivity : AppCompatActivity() {
    lateinit var binding:ActivityVacunaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityVacunaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSaveVacuna.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchVacuna.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditVacuna.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteVacuna.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val id_vacuna= binding.etIdVacuna.text.toString().trim().toInt()
        val nombre_vacuna= binding.etNombreVacuna.text.toString().trim()
        val id_laboratorio= binding.etIdLaboratorio.text.toString().trim().toInt()




        val registerData = VacunaDataCollectionItem(
            id_vacuna = null,
            nombre_vacuna = nombre_vacuna,
            id_laboratorio = id_laboratorio
        )

        addRegister(registerData) {
            if (it?.id_vacuna != null) {
                Toast.makeText(
                    this@VacunaActivity,
                    "Registro Realizado: " + it.id_vacuna,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@VacunaActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: VacunaDataCollectionItem,
        onResult: (VacunaDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(VacunaService::class.java)
        var result: Call<VacunaDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<VacunaDataCollectionItem> {
            override fun onFailure(call: Call<VacunaDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<VacunaDataCollectionItem>,
                response: retrofit2.Response<VacunaDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@VacunaActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@VacunaActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdVacuna.text.toString().toInt()
        val personService: VacunaService =
            RestEngine.buildService().create(VacunaService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@VacunaActivity,
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
                        this@VacunaActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@VacunaActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@VacunaActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_vacuna= binding.etIdVacuna.text.toString().trim().toInt()
        val nombre_vacuna= binding.etNombreVacuna.text.toString().trim()
        val id_laboratorio= binding.etIdLaboratorio.text.toString().trim().toInt()




        val registerData = VacunaDataCollectionItem(
            id_vacuna = id_vacuna,
            nombre_vacuna = nombre_vacuna,
            id_laboratorio = id_laboratorio
        )
        val retrofit = RestEngine.buildService().create(VacunaService::class.java)
        var result: Call<VacunaDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<VacunaDataCollectionItem> {
            override fun onFailure(call: Call<VacunaDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@VacunaActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<VacunaDataCollectionItem>,
                response: Response<VacunaDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@VacunaActivity,
                        "Registro Actualizado: " + response.body()!!.id_vacuna,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@VacunaActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@VacunaActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdVacuna.text.toString().toInt()
        val registerService: VacunaService =
            RestEngine.buildService().create(VacunaService::class.java)
        var result: Call<VacunaDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<VacunaDataCollectionItem> {
            override fun onFailure(call: Call<VacunaDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@VacunaActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<VacunaDataCollectionItem>,
                response: retrofit2.Response<VacunaDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@VacunaActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdVacuna.setText("" + response.body()!!.id_vacuna)
                        etNombreVacuna.setText("" + response.body()!!.nombre_vacuna)
                        etIdLaboratorio.setText("" + response.body()!!.id_laboratorio)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@VacunaActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@VacunaActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdVacuna.setText("")
            etNombreVacuna.setText("")
            etIdLaboratorio.setText("")
        }
    }
}