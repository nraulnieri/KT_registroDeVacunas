package daps.pango.vacunas_v1.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import daps.pango.vacunas_v1.Entities.UsuarioDataCollectionItem
import daps.pango.vacunas_v1.R
import daps.pango.vacunas_v1.RestApiError
import daps.pango.vacunas_v1.RestEngine
import daps.pango.vacunas_v1.Services.UsuarioService
import daps.pango.vacunas_v1.databinding.ActivityUsuarioBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioActivity : AppCompatActivity() {
    lateinit var binding:ActivityUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSaveUsuario.setOnClickListener {
            callServicePost()
        }

        binding.btnSearchUsuario.setOnClickListener {
            callServiceGet()
        }

        binding.btnEditUsuario.setOnClickListener {
            callServicePut()
        }

        binding.btnDeleteUsuario.setOnClickListener {
            callServiceDelete()
        }
    }

    private fun callServicePost() {


        //val id_usuario= binding.etIdUsuario.text.toString().trim().toInt()
        val usuario= binding.etUsuario.text.toString().trim()
        val contrasenxa= binding.etContrasenxa.text.toString().trim()
        val nombre_usuario= binding.etUsuarioNombre.text.toString().trim()



        val registerData = UsuarioDataCollectionItem(
            id_usuario = null,
            usuario = usuario,
            contrasenxa = contrasenxa,
            nombre_usuario = nombre_usuario
        )

        addRegister(registerData) {
            if (it?.id_usuario != null) {
                Toast.makeText(
                    this@UsuarioActivity,
                    "Registro Realizado: " + it.id_usuario,
                    Toast.LENGTH_LONG
                )
                    .show()
                LimpiarCampos()
            } else {
                Toast.makeText(
                    this@UsuarioActivity,
                    "Error, No se añadio el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun addRegister(
        registerData: UsuarioDataCollectionItem,
        onResult: (UsuarioDataCollectionItem?) -> Unit
    ) {
        val retrofit = RestEngine.buildService().create(UsuarioService::class.java)
        var result: Call<UsuarioDataCollectionItem> = retrofit.addRegistro(registerData)

        result.enqueue(object : Callback<UsuarioDataCollectionItem> {
            override fun onFailure(call: Call<UsuarioDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<UsuarioDataCollectionItem>,
                response: retrofit2.Response<UsuarioDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val addedRegister = response.body()!!
                    onResult(addedRegister)
                } else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@UsuarioActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@UsuarioActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        )
    }

    private fun callServiceDelete() {
        val id = binding.etIdUsuario.text.toString().toInt()
        val personService: UsuarioService =
            RestEngine.buildService().create(UsuarioService::class.java)
        var result: Call<ResponseBody> = personService.deleteRegistro(id)

        result.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@UsuarioActivity,
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
                        this@UsuarioActivity,
                        "Registro Eliminado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (response.code() == 401) {
                    Toast.makeText(this@UsuarioActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@UsuarioActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun callServicePut() {

        val id_usuario= binding.etIdUsuario.text.toString().trim().toInt()
        val usuario= binding.etUsuario.text.toString().trim()
        val contrasenxa= binding.etContrasenxa.text.toString().trim()
        val nombre_usuario= binding.etUsuarioNombre.text.toString().trim()



        val registerData = UsuarioDataCollectionItem(
            id_usuario = id_usuario,
            usuario = usuario,
            contrasenxa = contrasenxa,
            nombre_usuario = nombre_usuario
        )
        val retrofit = RestEngine.buildService().create(UsuarioService::class.java)
        var result: Call<UsuarioDataCollectionItem> = retrofit.updateRegistro(registerData)

        result.enqueue(object : Callback<UsuarioDataCollectionItem> {
            override fun onFailure(call: Call<UsuarioDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@UsuarioActivity,
                    "Error, No se Actualizo el Registro",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<UsuarioDataCollectionItem>,
                response: Response<UsuarioDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    val updatedRegister = response.body()!!
                    Toast.makeText(
                        this@UsuarioActivity,
                        "Registro Actualizado: " + response.body()!!.id_usuario,
                        Toast.LENGTH_LONG
                    ).show()
                    LimpiarCampos()
                } else if (response.code() == 401) {
                    Toast.makeText(this@UsuarioActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@UsuarioActivity,
                        "Error al buscar Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    private fun callServiceGet() {

        val id = binding.etIdUsuario.text.toString().toInt()
        val registerService: UsuarioService =
            RestEngine.buildService().create(UsuarioService::class.java)
        var result: Call<UsuarioDataCollectionItem> = registerService.getRegistro(id)

        result.enqueue(object : Callback<UsuarioDataCollectionItem> {
            override fun onFailure(call: Call<UsuarioDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@UsuarioActivity,
                    "Error al buscar Registro",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UsuarioDataCollectionItem>,
                response: retrofit2.Response<UsuarioDataCollectionItem>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@UsuarioActivity,
                        "Registro Encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                    with(binding) {
                        etIdUsuario.setText("" + response.body()!!.id_usuario)
                        etUsuario.setText("" + response.body()!!.usuario)
                        etContrasenxa.setText("" + response.body()!!.contrasenxa)
                        etUsuarioNombre.setText("" + response.body()!!.nombre_usuario)


                    }

                } else if (response.code() == 401) {
                    Toast.makeText(this@UsuarioActivity, "Sesion Finalizada", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@UsuarioActivity,
                        "Error al buscar el Registro",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    fun LimpiarCampos() {
        with(binding) {
            etIdUsuario.setText("")
            etUsuario.setText("")
            etContrasenxa.setText("")
            etUsuarioNombre.setText("")
        }
    }
}