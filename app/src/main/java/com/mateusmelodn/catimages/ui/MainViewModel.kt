package com.mateusmelodn.catimages.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateusmelodn.catimages.core.model.CatImages
import com.mateusmelodn.catimages.core.repo.CatImagesRepository
import com.mateusmelodn.catimages.util.AsyncState
import com.mateusmelodn.catimages.util.Fail
import com.mateusmelodn.catimages.util.Loading
import com.mateusmelodn.catimages.util.Success
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val catImagesRepository: CatImagesRepository): ViewModel() {
    val catImagesData = MutableLiveData<AsyncState<List<CatImages>>>()

    fun downloadCatImages(currentPage: Int) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            catImagesData.postValue(Fail(exception))
        }
        viewModelScope.launch(coroutineExceptionHandler) {
            catImagesData.postValue(Loading)
            catImagesRepository.downloadCatImages(currentPage).fold(

                {
                    catImagesData.postValue(Success(it))
                },
                {
                    catImagesData.postValue(Fail(it))
                }
            )
        }
    }
}