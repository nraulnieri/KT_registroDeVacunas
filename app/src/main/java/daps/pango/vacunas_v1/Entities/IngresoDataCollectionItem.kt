package daps.pango.vacunas_v1.Entities

import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class IngresoDataCollection:ArrayList<IngresoDataCollectionItem>()
data class IngresoDataCollectionItem (
    val id_ingreso:Int?,
    val id_laboratorio:Int,
    val fec_ingreso: String,
    val obs_ingreso:String,
        ) {
}