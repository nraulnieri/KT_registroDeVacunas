package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.UbicacionDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UbicacionService {
    @GET("ubicacion")
    fun listRegistros(): Call<List<UbicacionDataCollectionItem>>

    @GET("ubicacion/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<UbicacionDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("ubicacion")
    fun addRegistro(@Body registroData: UbicacionDataCollectionItem): Call<UbicacionDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("ubicacion")
    fun updateRegistro(@Body registroData: UbicacionDataCollectionItem): Call<UbicacionDataCollectionItem>

    @DELETE("ubicacion/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}