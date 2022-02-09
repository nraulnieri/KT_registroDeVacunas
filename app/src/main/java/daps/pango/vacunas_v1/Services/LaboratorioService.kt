package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.LaboratorioDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LaboratorioService {

    @GET("laboratorio")
    fun listRegistros(): Call<List<LaboratorioDataCollectionItem>>

    @GET("laboratorio/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<LaboratorioDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("laboratorio")
    fun addRegistro(@Body registroData: LaboratorioDataCollectionItem): Call<LaboratorioDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("laboratorio")
    fun updateRegistro(@Body registroData: LaboratorioDataCollectionItem): Call<LaboratorioDataCollectionItem>

    @DELETE("laboratorio/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}