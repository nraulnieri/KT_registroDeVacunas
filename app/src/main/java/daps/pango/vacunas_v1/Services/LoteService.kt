package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.LoteDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LoteService {
    @GET("lote")
    fun listRegistros(): Call<List<LoteDataCollectionItem>>

    @GET("lote/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<LoteDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("lote")
    fun addRegistro(@Body registroData: LoteDataCollectionItem): Call<LoteDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("lote")
    fun updateRegistro(@Body registroData: LoteDataCollectionItem): Call<LoteDataCollectionItem>

    @DELETE("lote/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}