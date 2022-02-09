package daps.pango.vacunas_v1.Entities

class  OcupacionDataCollection:ArrayList<OcupacionDataCollectionItem>()
data class OcupacionDataCollectionItem(
    val id_ocupacion:Int?,
    val nombre_ocupacion:String
) {
}