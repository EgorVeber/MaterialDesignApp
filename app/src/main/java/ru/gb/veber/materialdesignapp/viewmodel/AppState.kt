
import ru.gb.veber.materialdesignapp.model.*


sealed class AppState {
    data class Success(val pictureDTO: PictureDTO) : AppState()
    data class SuccessPOD(val serverResponseData: PODServerResponseData) : AppState()
    data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : AppState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()
    data class SuccessWeather(val solarFlareResponseData:List<SolarFlareResponseData>) : AppState()
    data class Error(val error: Throwable, val errorMessage: String) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
