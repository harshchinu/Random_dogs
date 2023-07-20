package com.example.doggo.ui.fragments.generate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doggo.data.service.dogService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenerateDogViewModel(
    private val generateDogRepository: GenerateDogRepository = GenerateDogRepository(dogService = dogService)
) : ViewModel() {

    private val _generateDog: MutableLiveData<GenerateDogEvent> = MutableLiveData()
    val generateDog: LiveData<GenerateDogEvent> = _generateDog

    fun getPartyName() {
        viewModelScope.launch(Dispatchers.IO) {
            _generateDog.postValue(GenerateDogEvent.Loading)
            val response = generateDogRepository.getRandomDog()
            if (response.isSuccess() && response.data != null) {
                _generateDog.postValue(GenerateDogEvent.RandomDogImageSuccess(response.data.message))
            } else {
                _generateDog.postValue(response.error?.let { GenerateDogEvent.RandomDogImageError(it) })
            }
        }
    }

    sealed class GenerateDogEvent() {
        data class RandomDogImageSuccess(val url: String) : GenerateDogEvent()
        data class RandomDogImageError(val error: String) : GenerateDogEvent()
        object Loading : GenerateDogEvent()
    }
}