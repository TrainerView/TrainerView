package com.trainerview.app.scenario

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.trainerview.app.feature.group_list.GroupListScreen
import com.trainerview.app.feature.update_group.UpdateGroupScreen
import com.trainerview.app.feature.update_participant.UpdateParticipantScreen

class CreateGroupScenario(
    private val groupName: String,
    private val participants: List<String> = emptyList()
) : Scenario() {
    override val steps: TestContext<Unit>.() -> Unit = {
        step("Открытие экрана обновления группы") {
            GroupListScreen { createGroupBtn.click() }
        }
        step("Ввод параметров группы") {
            UpdateGroupScreen {
                nameInput.typeText(groupName)
            }
        }
        participants.forEach { participantName ->
            step("Создание участника: $participantName") {
                UpdateGroupScreen {
                    addParticipantButton.click()
                }
                UpdateParticipantScreen {
                    nameInput.typeText(participantName)
                    saveButton.click()
                }
            }
        }
        step("Сохранение группы") {
            UpdateGroupScreen {
                saveButton.click()
            }
        }
    }
}