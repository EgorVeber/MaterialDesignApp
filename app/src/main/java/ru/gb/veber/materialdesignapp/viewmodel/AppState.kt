
import ru.gb.veber.materialdesignapp.model.PictureDTO


sealed class AppState {
    data class Success(val pictureDTO: PictureDTO) : AppState()
    data class Error(val error: Throwable, val errorMessage: String) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
