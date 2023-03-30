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
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @Named("real") private val imageSearchService: ImageSearchService
) : ViewModel() {
    private val _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    private val _images = MutableLiveData<List<ImgurImage>>(emptyList())
    val images: LiveData<List<ImgurImage>> = _images
    suspend fun doSearch(value: String) {
        _error.postValue( "")
        try {
            val list = imageSearchService.search(value)
            _images.postValue(list)
        }
        catch(ex:Error) {
            _error.postValue(ex.message?: kotlin.run { "" })
        }
    }

}