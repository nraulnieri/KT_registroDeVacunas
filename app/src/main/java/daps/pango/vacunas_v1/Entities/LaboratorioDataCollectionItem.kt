package daps.pango.vacunas_v1.Entities

class LaboratorioDataCollection:ArrayList<LaboratorioDataCollectionItem>()
data class LaboratorioDataCollectionItem(
    val id_laboratorio:Int?,
    val nombre_laboratorio:String,
    val id_ubicacion:Int,
    val direccion_laboratorio:String

) {
}