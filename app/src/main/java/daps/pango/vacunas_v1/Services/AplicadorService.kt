package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.AplicadorDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AplicadorService {
    @GET("aplicador")
    fun listRegistros(): Call<List<AplicadorDataCollectionItem>>

    @GET("aplicador/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<AplicadorDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("aplicador")
    fun addRegistro(@Body registroData: AplicadorDataCollectionItem): Call<AplicadorDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("aplicador")
    fun updateRegistro(@Body registroData: AplicadorDataCollectionItem): Call<AplicadorDataCollectionItem>

    @DELETE("aplicador/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}