package org.inu.coroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
) : ViewModel(){

    val btnText= MutableLiveData("hello Word")
    private  val dogRepository = DogRepository()

    fun getFacts(){
        viewModelScope.launch{
            when(val result = dogRepository.getDogRequest()){
                is Result.Success<GetData> -> btnText.value = result.data.facts[0]
                else -> btnText.value = "Error"
            }
        }
    }
}