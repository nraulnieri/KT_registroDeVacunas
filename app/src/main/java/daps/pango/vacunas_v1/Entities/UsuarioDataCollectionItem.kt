package daps.pango.vacunas_v1.Entities
class UsuarioDataCollection:ArrayList<UsuarioDataCollectionItem>()
data class UsuarioDataCollectionItem(
    val id_usuario:Int?,
    val usuario:String,
    val contrasenxa:String,
    val nombre_usuario:String
) {
}