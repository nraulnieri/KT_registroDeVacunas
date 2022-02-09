package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.CarnetDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface CarnetService {
    @GET("carnet")
    fun listRegistros(): Call<List<CarnetDataCollectionItem>>

    @GET("carnet/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<CarnetDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("carnet")
    fun addRegistro(@Body registroData: CarnetDataCollectionItem): Call<CarnetDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("carnet")
    fun updateRegistro(@Body registroData: CarnetDataCollectionItem): Call<CarnetDataCollectionItem>

    @DELETE("carnet/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}
