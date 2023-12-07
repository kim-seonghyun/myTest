package com.nhnacademy.shoppingmall.common.util;


import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    // 데이터 베이스 연결을 생성하고 관리하는 역할.
    private static final DataSource DATASOURCE;

    static {
        // Apache Comons DBCP 라이브러리에서 제공하는 DataSource 구현체.
        BasicDataSource basicDataSource = new BasicDataSource();

        try {
            basicDataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //todo#1-1 {ip},{database},{username},{password} 설정
        basicDataSource.setUrl("jdbc:mysql://133.186.241.167:3306/nhn_academy_6");
        basicDataSource.setUsername("nhn_academy_6");
        basicDataSource.setPassword("p63Amk!F0Mgvrd3&");

        //todo#1-2 initialSize, maxTotal, maxIdle, minIdle 은 모두 5로 설정합니다.
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        //todo#1-3 Validation Query를 설정하세요

        // BasicDataSource에서 연결이 유효한지 확인하는데 사용되는 SQL 질의문, mySql은 select 1
        basicDataSource.setValidationQuery("select 1");

        basicDataSource.setMaxWait(Duration.ofSeconds(2));

        //todo#1-4 적절히 변경하세요
        DATASOURCE = basicDataSource;

    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}
