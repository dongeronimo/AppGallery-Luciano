package com.luciano.test.appgalleryluciano.view.viewmodel
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luciano.test.appgalleryluciano.entity.ImgurImage
import com.luciano.test.appgalleryluciano.service.ImageSearchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

import java.io.IOException

import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @Named("real") private val imageSearchService: ImageSearchService
) : ViewModel() {
    private val _storePermisison = MutableLiveData(false)
    val storePermission: LiveData<Boolean> = _storePermisison
    private val _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    private val _images = MutableLiveData<List<ImgurImage>>(emptyList())
    val images: LiveData<List<ImgurImage>> = _images
    suspend fun doSearch(value: String) {
        _error.postValue( "")
        _images.postValue(emptyList())
        try {
            val list = imageSearchService.search(value)
            _images.postValue(list)
        }

        catch(ex:IOException) {
            _error.postValue("Erro de conexão - verifique sua internet")

        }
    }

    fun updateStorePermission(b: Boolean) {
        _storePermisison.postValue(b)
    }


    fun clearError() {
        _error.postValue("")
    }

}