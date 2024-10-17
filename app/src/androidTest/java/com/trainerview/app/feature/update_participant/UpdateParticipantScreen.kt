package com.trainerview.app.feature.update_participant

import com.kaspersky.kaspresso.screens.KScreen
import com.trainerview.app.R
import com.trainerview.app.feature.update_group.UpdateGroupScreen
import com.trainerview.app.features.update_participant_screen.UpdateParticipantFragment
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton

object UpdateParticipantScreen : KScreen<UpdateParticipantScreen>() {
    override val layoutId: Int = R.layout.fragment_update_participant
    override val viewClass: Class<*> = UpdateParticipantFragment::class.java

    val nameInput = KEditText { withId(R.id.name_input) }
    val saveButton = KButton { withId(R.id.save_button) }

}