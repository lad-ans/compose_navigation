package io.ladans.composenavigation.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ladans.composenavigation.models.DataLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SharedViewModel: ViewModel() {

    private val _sharedState = MutableStateFlow(DataLogin())
    val sharedState = _sharedState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    fun updateState(email: String? = null, password: String? = null) {
        _sharedState.value = DataLogin(email!!.addSuffix("***"), password!!)

        Log.d("TAG", "Dados Atualizados!!!!!!!!")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun navigateAsync(
        callback: ((value: String) -> Unit)? = null,
        callback2: (() -> Unit)? = null,
        secs: Duration? = null,
        minus: Duration? = null,
    ) {
        viewModelScope.launch {
            _loadingState.value = true
            delay(secs ?: minus ?: 2.seconds)
            callback?.invoke("home")
            callback2?.invoke()
            _loadingState.value = false
        }
    }
}

fun String.addSuffix(suffix: String):String {
    return "${this}$suffix"
}