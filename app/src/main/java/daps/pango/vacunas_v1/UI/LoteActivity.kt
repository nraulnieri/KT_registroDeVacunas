package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.LoteDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.LoteService
import daps.pango.vacunas_v1.databinding.ActivityLoteBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class LoteActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityLoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSaveLote.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchLote.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditLote.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteLote.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val num_lote= binding.etLoteNumLote.text.toString().trim().toInt()
        val id_vacuna= binding.etLoteIdVacuna.text.toString().trim().toInt()
        val cantidad_lote= binding.etLoteCantidad.text.toString().trim().toInt()
        val fecha_venc_lote= binding.etLoteFecVencimiento.text.toString().trim()

        val registerData = LoteDataCollectionItem(
            num_lote = null,
            id_vacuna = id_vacuna,
            cantidad_lote = cantidad_lote,
            fecha_venc_lote = fecha_venc_lote
        )

        addRegister(registerData) {
            if (it?.num_lote != null) {
                Toast.makeText(
                    this@LoteActivity,
                    "Registro Realizado: " + it.num_lote,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@LoteActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: LoteDataCollectionItem,
        onResult: (LoteDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(LoteService::class.java)
        var result: Call<LoteDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<LoteDataCollectionItem> {
            override fun onFailure(call: Call<LoteDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<LoteDataCollectionItem>,
                response: retrofit2.Response<LoteDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@LoteActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@LoteActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etLoteNumLote.text.toString().toInt()
        val personService: LoteService =
            RestEngine.buildService().create(LoteService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@LoteActivity,
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
                        this@LoteActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@LoteActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@LoteActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {
        val num_lote= binding.etLoteNumLote.text.toString().trim().toInt()
        val id_vacuna= binding.etLoteIdVacuna.text.toString().trim().toInt()
        val cantidad_lote= binding.etLoteCantidad.text.toString().trim().toInt()
        val fecha_venc_lote= binding.etLoteFecVencimiento.text.toString().trim()


        val registerData = LoteDataCollectionItem(
            num_lote = num_lote,
            id_vacuna = id_vacuna,
            cantidad_lote = cantidad_lote,
            fecha_venc_lote = fecha_venc_lote
        )
        val retrofit = RestEngine.buildService().create(LoteService::class.java)
        var result: Call<LoteDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<LoteDataCollectionItem> {
            override fun onFailure(call: Call<LoteDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@LoteActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<LoteDataCollectionItem>,
                response: Response<LoteDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@LoteActivity,
                        "Registro Actualizado: " + response.body()!!.num_lote,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@LoteActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@LoteActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etLoteNumLote.text.toString().toInt()
        val registerService: LoteService =
            RestEngine.buildService().create(LoteService::class.java)
        var result: Call<LoteDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<LoteDataCollectionItem> {
            override fun onFailure(call: Call<LoteDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@LoteActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LoteDataCollectionItem>,
                response: retrofit2.Response<LoteDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@LoteActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etLoteNumLote.setText("" + response.body()!!.num_lote)
                        etLoteIdVacuna.setText("" + response.body()!!.id_vacuna)
                        etLoteCantidad.setText("" + response.body()!!.cantidad_lote)
                        etLoteFecVencimiento.setText("" + response.body()!!.fecha_venc_lote)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@LoteActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@LoteActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etLoteNumLote.setText("" )
            etLoteIdVacuna.setText("" )
            etLoteCantidad.setText("" )
            etLoteFecVencimiento.setText("")
        }
    }
}