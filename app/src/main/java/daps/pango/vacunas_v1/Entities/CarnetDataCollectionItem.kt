package daps.pango.vacunas_v1.Entities

import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class CarnetDataCollection:ArrayList<CarnetDataCollectionItem>()

data class CarnetDataCollectionItem(
    val id_carnet:Int?,
    val id_persona:Int,
    val id_aplicador:Int,
    val id_usuario:Int,
    val fec_emision_carnet: String

) {

}