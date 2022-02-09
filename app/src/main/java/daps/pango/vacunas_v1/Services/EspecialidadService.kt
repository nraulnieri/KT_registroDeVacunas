package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.EspecialidadDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface EspecialidadService {
    @GET("especialidad")
    fun listRegistros(): Call<List<EspecialidadDataCollectionItem>>

    @GET("especialidad/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<EspecialidadDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("especialidad")
    fun addRegistro(@Body registroData: EspecialidadDataCollectionItem): Call<EspecialidadDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("especialidad")
    fun updateRegistro(@Body registroData: EspecialidadDataCollectionItem): Call<EspecialidadDataCollectionItem>

    @DELETE("especialidad/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}