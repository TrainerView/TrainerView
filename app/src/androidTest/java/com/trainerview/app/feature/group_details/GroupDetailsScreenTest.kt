package com.trainerview.app.feature.group_details

import com.trainerview.app.base.BaseTestCase
import com.trainerview.app.feature.group_list.GroupListScreen
import com.trainerview.app.scenario.CreateGroupScenario
import org.junit.Test

class GroupDetailsScreenTest : BaseTestCase() {

    @Test
    fun groupDetails() {
        run { scenario(CreateGroupScenario("group1")) }
        GroupListScreen {
            groupsRV {
                childAt<GroupListScreen.GroupItem>(0) { click() }
            }
        }
        GroupDetailsScreen {
            toolbar.hasTitle("Группа")
        }
    }
}