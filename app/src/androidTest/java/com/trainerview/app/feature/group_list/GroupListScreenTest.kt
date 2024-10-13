package com.trainerview.app.feature.group_list

import com.trainerview.app.base.BaseTestCase
import org.junit.Test

class GroupListScreenTest : BaseTestCase() {

    @Test
    fun test() {
        GroupListScreen {
            createGroupBtn.isGone()
        }
    }
}