package com.luciano.test.appgalleryluciano.view.viewmodel
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.luciano.test.appgalleryluciano.service.ImageSearchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @Named("mockado") imageSearchService: ImageSearchService
) : ViewModel() {
    suspend fun doSearch(value: String) {
        delay(1000)
    }

}