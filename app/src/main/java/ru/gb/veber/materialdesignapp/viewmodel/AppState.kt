
import ru.gb.veber.materialdesignapp.model.EarthEpicServerResponseData
import ru.gb.veber.materialdesignapp.model.MarsPhotosServerResponseData
import ru.gb.veber.materialdesignapp.model.PictureDTO


sealed class AppState {
    data class Success(val pictureDTO: PictureDTO) : AppState()
    data class SuccessListPicture(val pictureList: List<PictureDTO>) : AppState()
    data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : AppState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()
    data class Error(val error: Throwable, val errorMessage: String) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
