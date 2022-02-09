package daps.pango.vacunas_v1

data class RestApiError(val httpStatus: String, val errorMessage: String, val errorDetails: String) {
}