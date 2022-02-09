package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.DetCarnetDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DetCarnetService {
    @GET("detcarnet")
    fun listRegistros(): Call<List<DetCarnetDataCollectionItem>>

    @GET("detcarnet/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<DetCarnetDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("detcarnet")
    fun addRegistro(@Body registroData: DetCarnetDataCollectionItem): Call<DetCarnetDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("detcarnet")
    fun updateRegistro(@Body registroData: DetCarnetDataCollectionItem): Call<DetCarnetDataCollectionItem>

    @DELETE("detcarnet/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}