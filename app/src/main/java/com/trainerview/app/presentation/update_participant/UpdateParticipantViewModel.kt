package com.trainerview.app.presentation.update_participant

import androidx.core.os.bundleOf
import com.trainerview.app.base.BackWithResult
import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.presentation.group_details.GroupDetailsFragmentArgs
import com.trainerview.app.presentation.update_group.UpdateGroupNavParams
import com.trainerview.app.presentation.update_participant.UpdateParticipantFragment.Companion.ADD_PARTICIPANT_MODEL_KEY
import com.trainerview.app.presentation.update_participant.UpdateParticipantFragment.Companion.ADD_PARTICIPANT_REQUEST_KEY
import javax.inject.Inject

class UpdateParticipantViewModel @Inject constructor(

) : BaseViewModel() {

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