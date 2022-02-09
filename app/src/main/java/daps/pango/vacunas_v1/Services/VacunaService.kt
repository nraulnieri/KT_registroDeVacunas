package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.VacunaDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface VacunaService {
    @GET("vacuna")
    fun listRegistros(): Call<List<VacunaDataCollectionItem>>

    @GET("vacuna/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<VacunaDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("vacuna")
    fun addRegistro(@Body registroData: VacunaDataCollectionItem): Call<VacunaDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("vacuna")
    fun updateRegistro(@Body registroData: VacunaDataCollectionItem): Call<VacunaDataCollectionItem>

    @DELETE("vacuna/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}