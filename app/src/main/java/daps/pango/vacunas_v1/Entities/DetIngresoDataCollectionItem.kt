package daps.pango.vacunas_v1.Entities


class  DetIngresoDataCollection:ArrayList<DetCarnetDataCollectionItem>()
data class DetIngresoDataCollectionItem(
    val id_det_ingreso:Int?,
    val id_cabecera_ingreso:Int,
    val num_lote:Int

) {
}