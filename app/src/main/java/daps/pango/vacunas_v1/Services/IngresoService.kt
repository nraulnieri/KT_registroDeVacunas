package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.IngresoDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IngresoService {
    @GET("ingreso")
    fun listRegistros(): Call<List<IngresoDataCollectionItem>>

    @GET("ingreso/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<IngresoDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("ingreso")
    fun addRegistro(@Body registroData: IngresoDataCollectionItem): Call<IngresoDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("ingreso")
    fun updateRegistro(@Body registroData: IngresoDataCollectionItem): Call<IngresoDataCollectionItem>

    @DELETE("ingreso/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}