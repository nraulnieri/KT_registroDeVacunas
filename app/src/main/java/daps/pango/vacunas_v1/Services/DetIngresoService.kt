package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.DetIngresoDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DetIngresoService {
    @GET("detingreso")
    fun listRegistros(): Call<List<DetIngresoDataCollectionItem>>

    @GET("detingreso/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<DetIngresoDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("detingreso")
    fun addRegistro(@Body registroData: DetIngresoDataCollectionItem): Call<DetIngresoDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("detingreso")
    fun updateRegistro(@Body registroData: DetIngresoDataCollectionItem): Call<DetIngresoDataCollectionItem>

    @DELETE("detingreso/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}