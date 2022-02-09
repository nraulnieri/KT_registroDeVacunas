package daps.pango.vacunas_v1.Entities

class VacunaDataCollection:ArrayList<VacunaDataCollectionItem>()

data class VacunaDataCollectionItem(
    val id_vacuna:Int?,
    val nombre_vacuna:String,
    val id_laboratorio:Int
) {
}