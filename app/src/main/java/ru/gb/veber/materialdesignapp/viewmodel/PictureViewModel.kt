import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.model.PictureRetrofit
import ru.gb.veber.materialdesignapp.utils.EMPTY_RESPONSE
import ru.gb.veber.materialdesignapp.utils.ERROR_FAILURE

class PictureViewModel(
    private val liveData: MutableLiveData<PictureState> = MutableLiveData(),
    private val pictureRetrofit: PictureRetrofit = PictureRetrofit()
) : ViewModel() {

    fun sendServerRequest(date: String) {
        liveData.postValue(PictureState.Loading(null))
        pictureRetrofit.getPicture(callback, date)
    }

    fun getLiveData() = liveData

    private val callback = object : Callback<PictureDTO> {
        override fun onResponse(
            call: Call<PictureDTO>,
            response: Response<PictureDTO>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureState.Success(it))
                }
            } else {
                liveData.postValue(PictureState.Error(Throwable(), EMPTY_RESPONSE))
            }
        }

        override fun onFailure(call: Call<PictureDTO>, t: Throwable) {
            liveData.postValue(PictureState.Error(Throwable(), ERROR_FAILURE))
        }
    }
}