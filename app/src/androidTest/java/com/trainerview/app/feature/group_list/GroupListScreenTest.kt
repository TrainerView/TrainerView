package com.trainerview.app.feature.group_list

import com.trainerview.app.base.BaseTestCase
import com.trainerview.app.feature.group_details.GroupDetailsScreen
import com.trainerview.app.feature.group_list.GroupListScreen.groupsRV
import com.trainerview.app.feature.update_group.UpdateGroupScreen
import com.trainerview.app.scenario.CreateGroupScenario
import org.junit.Assert
import org.junit.Test

class GroupListScreenTest : BaseTestCase() {

    @Test
    fun createGroup() {
        run { scenario(CreateGroupScenario("group1")) }
        GroupListScreen {
            Assert.assertEquals(1, groupsRV.getSize())
            groupsRV {
                childAt<GroupListScreen.GroupItem>(0) {
                    title.hasText("gr2oup1")
                }
            }
        }
    }
}