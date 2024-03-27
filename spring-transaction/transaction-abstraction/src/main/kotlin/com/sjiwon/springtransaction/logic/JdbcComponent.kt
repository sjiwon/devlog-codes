package com.sjiwon.springtransaction.logic

import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.sql.DataSource

@Component
class JdbcComponent(
    private val dataSource: DataSource,
) {
    fun logic(exception: Boolean) {
        var connection: Connection? = null
        var pstmt: PreparedStatement?

        try {
            connection = dataSource.connection
            connection.autoCommit = false

            // 1. 사용자 정보 저장
            pstmt = connection.prepareStatement(
                "INSERT INTO members(name) VALUES (?)",
                PreparedStatement.RETURN_GENERATED_KEYS,
            )
            pstmt.setString(1, "Member")
            pstmt.executeUpdate()

            // 2. 사용자 개인 사물함 정보 저장
            val rs: ResultSet = pstmt.generatedKeys
            if (rs.next()) {
                pstmt = connection.prepareStatement(
                    "INSERT INTO buckets(member_id, capacity) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS,
                )
                pstmt.setLong(1, rs.getLong(1))
                pstmt.setInt(2, 10)
                pstmt.executeUpdate()
            }

            // 예외 발생? All or Nothing?
            if (exception) {
                throw RuntimeException()
            }

            connection.commit()
        } catch (ex: Exception) {
            connection?.rollback()
        }
    }
}
