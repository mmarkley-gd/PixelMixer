package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    val totalMoves = MutableLiveData(0)
    val elapsedTime = MutableLiveData("1/1/1970")
}