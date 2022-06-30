import ru.gb.veber.materialdesignapp.model.EarthEpicServerResponseData
import ru.gb.veber.materialdesignapp.model.MarsPhotosServerResponseData
import ru.gb.veber.materialdesignapp.model.PictureDTO

sealed class PictureState {
    data class Success(val pictureDTO: PictureDTO) : PictureState()
    data class Error(val error: Throwable, val errorMessage: String) : PictureState()
    data class Loading(val progress: Int?) : PictureState()
}

sealed class ListPictureState {
    data class Success(val pictureList: List<PictureDTO>) : ListPictureState()
    data class Error(val error: Throwable, val errorMessage: String) : ListPictureState()
    data class Loading(val progress: Int?) : ListPictureState()
}

sealed class EarthState {
    data class Success(val serverResponseData: List<EarthEpicServerResponseData>) : EarthState()
    data class Error(val error: Throwable, val errorMessage: String) : EarthState()
    data class Loading(val progress: Int?) : EarthState()
}

sealed class MarsState {
    data class Success(val serverResponseData: MarsPhotosServerResponseData) : MarsState()
    data class Error(val error: Throwable, val errorMessage: String) : MarsState()
    data class Loading(val progress: Int?) : MarsState()
}