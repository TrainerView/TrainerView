package com.trainerview.app.base

import androidx.test.core.app.ApplicationProvider
import com.trainerview.app.app.App
import com.trainerview.app.base.db.RoomConstants
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

object ClearDbRule : TestRule {

    private val app: App by lazy {
        ApplicationProvider.getApplicationContext()
    }

    override fun apply(base: Statement, description: Description): Statement {
        app.deleteDatabase(RoomConstants.DATA_BASE_NAME)
        return base
    }
}