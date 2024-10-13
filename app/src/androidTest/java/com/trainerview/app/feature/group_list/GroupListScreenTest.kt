package com.trainerview.app.feature.group_list

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.trainerview.app.app.MainActivity
import org.junit.Rule
import org.junit.Test

class GroupListScreenTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() {
        GroupListScreen {
            createGroupBtn.isVisible()
        }
    }
}