package com.trainerview.app.feature.group_details

import com.kaspersky.kaspresso.screens.KScreen
import com.trainerview.app.R
import com.trainerview.app.features.group_details_screen.GroupDetailsFragment
import io.github.kakaocup.kakao.toolbar.KToolbar

object GroupDetailsScreen: KScreen<GroupDetailsScreen>() {
    override val layoutId: Int = R.layout.fragment_group_details
    override val viewClass: Class<*> = GroupDetailsFragment::class.java

    val toolbar = KToolbar { withId(R.id.toolbar) }
}