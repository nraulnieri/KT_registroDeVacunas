package daps.pango.vacunas_v1.Entities

import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class  LoteDataCollection:ArrayList<LoteDataCollectionItem>()
data class LoteDataCollectionItem(
    val num_lote:Int?,
    val id_vacuna:Int,
    val cantidad_lote:Int,
    val fecha_venc_lote: String
) {
}