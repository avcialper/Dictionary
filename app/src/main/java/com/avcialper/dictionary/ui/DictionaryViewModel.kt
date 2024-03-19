package com.avcialper.dictionary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.avcialper.dictionary.domain.model.WordInfo
import com.avcialper.dictionary.domain.use_case.GetWordInfo
import com.avcialper.dictionary.util.constants.ResourceStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    private var _data = MutableLiveData<List<WordInfo>>()
    val data: LiveData<List<WordInfo>> = _data

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun onSearch(query: String) = viewModelScope.launch {
        delay(500L)
        getWordInfo(query).asLiveData(viewModelScope.coroutineContext).observeForever {
            when (it.status) {
                ResourceStatus.SUCCESS -> {
                    _data.value = it.data!!
                    _loading.value = false
                }

                ResourceStatus.LOADING -> {
                    _loading.value = true
                }

                ResourceStatus.ERROR -> {
                    _error.value = it.throwable!!
                    _loading.value = false
                    _errorMessage.value = it.errorMessage
                }
            }
        }
    }

}