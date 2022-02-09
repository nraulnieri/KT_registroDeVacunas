package daps.pango.vacunas_v1.Entities

class AplicadorDataCollection:ArrayList<AplicadorDataCollectionItem>()

data class AplicadorDataCollectionItem (
    val id_aplicador:Int?,
    val nombre_aplicador:String,
    val id_especialidad:Int,
    val id_centro_vacunacion:Int

)