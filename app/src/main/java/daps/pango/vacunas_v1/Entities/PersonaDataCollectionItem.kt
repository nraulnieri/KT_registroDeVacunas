package daps.pango.vacunas_v1.Entities

import java.math.BigInteger
import java.time.LocalDate

class  PersonaDataCollection:ArrayList<PersonaDataCollectionItem>()
data class PersonaDataCollectionItem(
    val id_persona:Int?,
    val dni_persona: BigInteger,
    val nombre_persona:String,
    val fec_nac_persona: String,
    val genero:String,
    val id_ocupacion:Int,
    val obs_ocupacion_persona:String,
    val id_ubicacion:Int,
    val obs_ubicacion_persona:String
) {
}