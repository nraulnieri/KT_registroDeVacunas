package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.UsuarioDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {
    @GET("usuario")
    fun listRegistros(): Call<List<UsuarioDataCollectionItem>>

    @GET("usuario/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<UsuarioDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("usuario")
    fun addRegistro(@Body registroData: UsuarioDataCollectionItem): Call<UsuarioDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("usuario")
    fun updateRegistro(@Body registroData: UsuarioDataCollectionItem): Call<UsuarioDataCollectionItem>

    @DELETE("usuario/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}