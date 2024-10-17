package com.trainerview.app.feature.update_group

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import com.trainerview.app.R
import com.trainerview.app.features.update_group_screen.UpdateGroupFragment
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object UpdateGroupScreen: KScreen<UpdateGroupScreen>() {
    override val layoutId: Int = R.layout.fragment_update_group
    override val viewClass: Class<*> = UpdateGroupFragment::class.java

    val nameInput = KEditText { withId(R.id.name_input) }
    val saveButton = KImageView { withId(R.id.save_button) }

    val addParticipantButton = KView { withId(R.id.add_participant_button) }

    val participantsRV = KRecyclerView(
        builder = { withId(R.id.participant_rv) },
        itemTypeBuilder = { itemType(::ParticipantItem) }
    )

    class ParticipantItem(matcher: Matcher<View>): KRecyclerItem<ParticipantItem>(matcher) {
        val title = KTextView(matcher) { withId(R.id.title) }
    }

}