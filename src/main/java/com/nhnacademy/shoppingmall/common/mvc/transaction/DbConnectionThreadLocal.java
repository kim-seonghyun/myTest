package com.nhnacademy.shoppingmall.common.mvc.transaction;

import com.nhnacademy.shoppingmall.common.util.DbUtils;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

/**
 * DB연결을 ThreadLocal에 저장하고 관리. 각 Thread는 독립적인 connection을 가짐.
 */
@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(() -> false);

    /**
     * connection pool에서 connectionThreadLocal에 connection을 할당합니다.
     */
    public static void initialize() {
        // 여기서 connection Pool은 DBCP 말하는거 맞나?
        //todo#2-1 - connection pool에서 connectionThreadLocal에 connection을 할당합니다.
        // 각 트랜잭션에서의 변경 내용이 commit이나 rollback 여부에 상관없이 다른 트랜잭셭에서 값을 읽을 수 있음.
        try (Connection connection = DbUtils.getDataSource().getConnection();) {

            //todo#2-2 connectiond의 Isolation level을 READ_COMMITED를 설정 합니다.
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            //todo#2-3 auto commit 을 false로 설정합니다.

            connection.setAutoCommit(false);
            connectionThreadLocal.set(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 현재 thread의 connection 반환.
     *
     * @return
     */
    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    /**
     * SQL 에러 발생 여부를 설정
     *
     * @param sqlError
     */
    public static void setSqlError(boolean sqlError) {
        sqlErrorThreadLocal.set(sqlError);
    }

    /**
     * @return SQL error 발생 여부
     */
    public static boolean getSqlError() {
        return sqlErrorThreadLocal.get();
    }

    public static void reset() {

        //todo#2-4 사용이 완료된 connection은 close를 호출하여 connection pool에 반환합니다.
        try (Connection connection = connectionThreadLocal.get();
        ) {
            if (getSqlError()) {
                //todo#2-5 getSqlError() 에러가 존재하면 rollback 합니다.
                connection.rollback();
            } else {
                //todo#2-6 getSqlError() 에러가 존재하지 않다면 commit 합니다.
                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                //todo#2-7 현제 사용하고 있는 connection을 재 사용할 수 없도록 connectionThreadLocal을 초기화 합니다.
                connectionThreadLocal.remove();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
