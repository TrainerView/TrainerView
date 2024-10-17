package com.trainerview.app.feature.group_list

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import com.trainerview.app.features.group_details_screen.GroupDetailsFragment
import com.trainerview.app.R
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerItemType
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object GroupListScreen : KScreen<GroupListScreen>() {
    override val layoutId: Int = R.layout.fragment_group_list
    override val viewClass: Class<*> = GroupDetailsFragment::class.java

    val editGroupBtn = KButton { withId(R.id.edit_group_button) }
    val createGroupBtn = KButton { withId(R.id.create_group_btn) }

    val groupsRV = KRecyclerView(
        builder = { withId(R.id.groups_rv) },
        itemTypeBuilder = { itemType(::GroupItem) }
    )

    class GroupItem(matcher: Matcher<View>): KRecyclerItem<GroupItem>(matcher) {
        val title = KTextView(matcher) { withId(R.id.title) }
    }
}