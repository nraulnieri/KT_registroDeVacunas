package daps.pango.vacunas_v1.Entities


class CentroDataCollection:ArrayList<CentroDataCollectionItem>()
data class CentroDataCollectionItem(
    val id_centro_vacunacion:Int?,
    val nombre_centro_vacunacion:String,
    val direccion_centro_vacunacion:String,
    val id_ubicacion:Int

) {
}