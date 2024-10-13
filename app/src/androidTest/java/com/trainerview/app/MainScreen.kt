package com.trainerview.app

import com.kaspersky.kaspresso.screens.KScreen
import com.trainerview.app.presentation.group_details.GroupDetailsFragment
import io.github.kakaocup.kakao.text.KButton

object MainScreen : KScreen<MainScreen>() {
    override val layoutId: Int = R.layout.fragment_group_list
    override val viewClass: Class<*> = GroupDetailsFragment::class.java

    val createGroupBtn = KButton { withId(R.id.create_group_btn) }
}