package com.trainerview.app.feature.group_list

import com.kaspersky.kaspresso.screens.KScreen
import com.trainerview.app.features.group_details_screen.GroupDetailsFragment
import com.trainerview.app.R
import io.github.kakaocup.kakao.text.KButton

object GroupListScreen : KScreen<GroupListScreen>() {
    override val layoutId: Int = R.layout.fragment_group_list
    override val viewClass: Class<*> = GroupDetailsFragment::class.java

    val createGroupBtn = KButton { withId(R.id.create_group_btn) }
}