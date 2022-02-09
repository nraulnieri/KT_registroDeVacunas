package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.OcupacionDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface OcupacionService {
    @GET("ocupacion")
    fun listRegistros(): Call<List<OcupacionDataCollectionItem>>

    @GET("ocupacion/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<OcupacionDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("ocupacion")
    fun addRegistro(@Body registroData: OcupacionDataCollectionItem): Call<OcupacionDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("ocupacion")
    fun updateRegistro(@Body registroData: OcupacionDataCollectionItem): Call<OcupacionDataCollectionItem>

    @DELETE("ocupacion/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}