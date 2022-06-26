import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.model.PictureRetrofit
import ru.gb.veber.materialdesignapp.utils.EMPTY_RESPONSE
import ru.gb.veber.materialdesignapp.utils.ERROR_FAILURE

class ListPictureViewModel(
    private val liveData: MutableLiveData<ListPictureState> = MutableLiveData(),
    private val pictureRetrofit: PictureRetrofit = PictureRetrofit()
) : ViewModel() {

    fun sendServerRequest(start_date: String, end_date: String) {
        liveData.postValue(ListPictureState.Loading(null))
        pictureRetrofit.getListPicture(callback, start_date, end_date)
    }

    fun getLiveData() = liveData

    private val callback = object : Callback<List<PictureDTO>> {
        override fun onResponse(
            call: Call<List<PictureDTO>>,
            response: Response<List<PictureDTO>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(ListPictureState.Success(it))
                }
            } else {
                liveData.postValue(ListPictureState.Error(Throwable(), EMPTY_RESPONSE))
            }
        }

        override fun onFailure(call: Call<List<PictureDTO>>, t: Throwable) {
            liveData.postValue(ListPictureState.Error(Throwable(), ERROR_FAILURE))
        }
    }
}