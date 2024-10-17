package com.trainerview.app.feature.update_participant

import com.trainerview.app.base.BaseTestCase
import com.trainerview.app.feature.group_list.GroupListScreen
import com.trainerview.app.feature.update_group.UpdateGroupScreen
import com.trainerview.app.scenario.CreateGroupScenario
import org.junit.Test

class UpdateParticipantScreenTest : BaseTestCase() {

    @Test
    fun addParticipant() {
        run {
            scenario(
                CreateGroupScenario(
                    groupName = "group1",
                    participants = listOf("participant1")
                )
            )
        }
        GroupListScreen {
            groupsRV {
                childAt<GroupListScreen.GroupItem>(0) { longClick() }
            }
        }
        GroupListScreen {
            editGroupBtn.click()
        }
        UpdateGroupScreen {
            participantsRV {
                childAt<UpdateGroupScreen.ParticipantItem>(0) {
                    title.hasText("participant1")
                }
            }
        }
    }
}