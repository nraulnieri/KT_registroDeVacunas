package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.CentroDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.CentroService
import daps.pango.vacunas_v1.databinding.ActivityCentroVacunacionBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CentroDeVacunacionActivity : AppCompatActivity() {
    lateinit var binding:ActivityCentroVacunacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityCentroVacunacionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnSaveCentroVacunacion.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchCentroVacunacion.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditCentroVacunacion.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteCentroVacunacion.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val id_centro_vacunacion=binding.etIdHospital.text.toString().trim().toInt()
        val nombre_centro_vacunacion = binding.etNombreHospital.text.toString().trim()
        val direccion_centro_vacunacion = binding.etDirHospital.text.toString().trim()
        val id_ubicacion= binding.etHospitalIbUbicacion.text.toString().trim().toInt()


        val registerData = CentroDataCollectionItem(
            id_centro_vacunacion = null,
            nombre_centro_vacunacion = nombre_centro_vacunacion,
            direccion_centro_vacunacion = direccion_centro_vacunacion,
            id_ubicacion = id_ubicacion
        )

        addRegister(registerData) {
            if (it?.id_centro_vacunacion != null) {
                Toast.makeText(
                    this@CentroDeVacunacionActivity,
                    "Registro Realizado: " + it.id_centro_vacunacion,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@CentroDeVacunacionActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: CentroDataCollectionItem,
        onResult: (CentroDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(CentroService::class.java)
        var result: Call<CentroDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<CentroDataCollectionItem> {
            override fun onFailure(call: Call<CentroDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<CentroDataCollectionItem>,
                response: retrofit2.Response<CentroDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@CentroDeVacunacionActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@CentroDeVacunacionActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdHospital.text.toString().toInt()
        val personService: CentroService =
            RestEngine.buildService().create(CentroService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@CentroDeVacunacionActivity,
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
                        this@CentroDeVacunacionActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@CentroDeVacunacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@CentroDeVacunacionActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val id_centro_vacunacion=binding.etIdHospital.text.toString().trim().toInt()
        val nombre_centro_vacunacion = binding.etNombreHospital.text.toString().trim()
        val direccion_centro_vacunacion = binding.etDirHospital.text.toString().trim()
        val id_ubicacion= binding.etHospitalIbUbicacion.text.toString().trim().toInt()


        val registerData = CentroDataCollectionItem(
            id_centro_vacunacion = id_centro_vacunacion,
            nombre_centro_vacunacion = nombre_centro_vacunacion,
            direccion_centro_vacunacion = direccion_centro_vacunacion,
            id_ubicacion = id_ubicacion
        )
        val retrofit = RestEngine.buildService().create(CentroService::class.java)
        var result: Call<CentroDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<CentroDataCollectionItem> {
            override fun onFailure(call: Call<CentroDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@CentroDeVacunacionActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<CentroDataCollectionItem>,
                response: Response<CentroDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@CentroDeVacunacionActivity,
                        "Registro Actualizado: " + response.body()!!.id_centro_vacunacion,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@CentroDeVacunacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@CentroDeVacunacionActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdHospital.text.toString().toInt()
        val registerService: CentroService =
            RestEngine.buildService().create(CentroService::class.java)
        var result: Call<CentroDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<CentroDataCollectionItem> {
            override fun onFailure(call: Call<CentroDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@CentroDeVacunacionActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<CentroDataCollectionItem>,
                response: retrofit2.Response<CentroDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CentroDeVacunacionActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdHospital.setText("" + response.body()!!.id_centro_vacunacion)
                        etDirHospital.setText("" + response.body()!!.direccion_centro_vacunacion)
                        etHospitalIbUbicacion.setText("" + response.body()!!.id_ubicacion)
                        etNombreHospital.setText("" + response.body()!!.nombre_centro_vacunacion)



                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@CentroDeVacunacionActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@CentroDeVacunacionActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdHospital.setText("" )
            etDirHospital.setText("" )
            etHospitalIbUbicacion.setText("")
            etNombreHospital.setText("")
        }
    }
}
