package com.rogeriogregorio.securityloginjwt.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class CustomHealthCheck implements HealthIndicator {

    @Autowired
    private DataSource dataSource;

    @Override
    public Health health() {

        boolean databaseIsUp = checkDatabaseConnection();
        Health databaseUp = Health.up().withDetail("database", "up").build();
        Health databaseDown = Health.down().withDetail("database", "down").build();

        return databaseIsUp ? databaseUp : databaseDown;
    }

    private boolean checkDatabaseConnection() {

        try (Connection connection = dataSource.getConnection()) {

            if (connection != null && !connection.isClosed()) {
                return true;
            }

        } catch (SQLException e) {
            return false;
        }

        return false;
    }
}


