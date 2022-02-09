package daps.pango.vacunas_v1.Services

import daps.pango.vacunas_v1.Entities.PersonaDataCollectionItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PersonaService {
    @GET("persona")
    fun listRegistros(): Call<List<PersonaDataCollectionItem>>

    @GET("persona/id/{id}")
    fun getRegistro(@Path("id") idRegistro: Int): Call<PersonaDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("persona")
    fun addRegistro(@Body registroData: PersonaDataCollectionItem): Call<PersonaDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("persona")
    fun updateRegistro(@Body registroData: PersonaDataCollectionItem): Call<PersonaDataCollectionItem>

    @DELETE("persona/id/{id}")
    fun deleteRegistro(@Path("id") idRegistro: Int): Call<ResponseBody>
}