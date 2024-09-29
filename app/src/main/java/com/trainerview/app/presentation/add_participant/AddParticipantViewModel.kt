package com.trainerview.app.presentation.add_participant

import androidx.lifecycle.viewModelScope
import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.domain.ParticipantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddParticipantViewModel @Inject constructor(
    private val repository: ParticipantRepository
) : BaseViewModel() {

//    fun createParticipant(name: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val participant = ParticipantDb(name = name)
//            repository.insertParticipant(participant)
//        }
//    }
}