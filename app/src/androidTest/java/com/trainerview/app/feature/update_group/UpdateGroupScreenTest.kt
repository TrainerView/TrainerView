package com.trainerview.app.feature.update_group

import com.trainerview.app.base.BaseTestCase
import com.trainerview.app.feature.group_list.GroupListScreen
import com.trainerview.app.scenario.CreateGroupScenario
import org.junit.Test

class UpdateGroupScreenTest : BaseTestCase() {

    @Test
    fun editGroup() {
        run { scenario(CreateGroupScenario("group1")) }
        GroupListScreen {
            groupsRV {
                childAt<GroupListScreen.GroupItem>(0) { longClick() }
            }
        }
        GroupListScreen {
            editGroupBtn.click()
        }
        UpdateGroupScreen {
            nameInput.hasText("group1")
        }
    }
}