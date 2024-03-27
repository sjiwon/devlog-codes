package com.sjiwon.springtransaction.common

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class DatabaseCleanerEachCallbackExtension : AfterEachCallback {
    override fun afterEach(context: ExtensionContext) {
        SpringExtension
            .getApplicationContext(context)
            .getBean(DatabaseCleaner::class.java)
            .cleanUpDatabase()
    }
}
