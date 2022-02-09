package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.CentroDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface CentroService {
    @GET("centro")
    fun listRegistros(): Call<List<CentroDataCollectionItem>>

    @GET("centro/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<CentroDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("centro")
    fun addRegistro(@Body registroData: CentroDataCollectionItem): Call<CentroDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("centro")
    fun updateRegistro(@Body registroData: CentroDataCollectionItem): Call<CentroDataCollectionItem>

    @DELETE("centro/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}