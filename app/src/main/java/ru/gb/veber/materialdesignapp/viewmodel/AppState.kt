package com.gb.m_1975_3.viewmodel

import ru.gb.veber.materialdesignapp.model.PictureDTO


sealed class AppState {
    data class Success(val pictureDTO: PictureDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
