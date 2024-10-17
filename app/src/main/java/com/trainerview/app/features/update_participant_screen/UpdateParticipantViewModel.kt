package com.trainerview.app.features.update_participant_screen

import androidx.core.os.bundleOf
import com.trainerview.app.base.ui.BackWithResult
import com.trainerview.app.base.ui.BaseViewModel
import com.trainerview.app.features.update_participant_screen.UpdateParticipantFragment.Companion.ADD_PARTICIPANT_MODEL_KEY
import com.trainerview.app.features.update_participant_screen.UpdateParticipantFragment.Companion.ADD_PARTICIPANT_REQUEST_KEY
import javax.inject.Inject

class UpdateParticipantViewModel @Inject constructor() : BaseViewModel() {

    private val args by navArgs<UpdateParticipantFragmentArgs>()

    fun onSaveClick(name: String) {
        val participant = when (val params = args.updateParams) {
            UpdateParticipantNavParams.CreateParticipant -> UpdateParticipantModel(
                id = null,
                name = name
            )
            is UpdateParticipantNavParams.UpdateParticipant -> UpdateParticipantModel(
                id = params.participantId,
                name = name
            )
        }
        postNavEvents(
            BackWithResult(
                ADD_PARTICIPANT_REQUEST_KEY,
                bundleOf(ADD_PARTICIPANT_MODEL_KEY to participant)
            )
        )
    }
}
