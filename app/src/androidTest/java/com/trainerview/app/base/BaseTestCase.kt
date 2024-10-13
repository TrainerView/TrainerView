package com.trainerview.app.base

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.trainerview.app.app.MainActivity
import org.junit.Rule
import org.junit.rules.RuleChain

open class BaseTestCase : TestCase() {

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(ActivityScenarioRule(MainActivity::class.java))
        .around(ClearDbRule)
}