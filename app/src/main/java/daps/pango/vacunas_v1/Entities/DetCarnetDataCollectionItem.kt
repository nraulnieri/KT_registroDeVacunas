package daps.pango.vacunas_v1.Entities


class DetCarnetDataCollection:ArrayList<DetCarnetDataCollectionItem>()
data class DetCarnetDataCollectionItem(
    val id_detcarnet:Int?,
    val id_carnet:Int,
    val id_medico:Int,
    val id_laboratorio:Int,
    val num_lote:Int
) {
}