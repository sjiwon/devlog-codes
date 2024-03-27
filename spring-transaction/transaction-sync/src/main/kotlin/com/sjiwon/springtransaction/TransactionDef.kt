package com.sjiwon.springtransaction

import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.transaction.support.TransactionTemplate

@Component
class TransactionDef(
    private val transactionManager: PlatformTransactionManager,
) {
    fun execute() {
        val writebleTx = TransactionTemplate(transactionManager).apply {
            isReadOnly = false
        }
        val readOnlyTx = TransactionTemplate(transactionManager).apply {
            isReadOnly = true
        }

        println("## WritableTx -> ReadOnlyTx ##")
        writebleTx.executeWithoutResult {
            println("1. ReadOnly = ${TransactionSynchronizationManager.isCurrentTransactionReadOnly()}")
            readOnlyTx.executeWithoutResult {
                println("2. ReadOnly = ${TransactionSynchronizationManager.isCurrentTransactionReadOnly()}")
            }
        }

        println("\n## ReadOnlyTx -> WritableTx ##")
        readOnlyTx.executeWithoutResult {
            println("1. ReadOnly = ${TransactionSynchronizationManager.isCurrentTransactionReadOnly()}")
            writebleTx.executeWithoutResult {
                println("2. ReadOnly = ${TransactionSynchronizationManager.isCurrentTransactionReadOnly()}")
            }
        }
    }
}
