package com.rogeriogregorio.securityloginjwt.health;

import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class CustomHealthCheck implements HealthIndicator {

    private final CatchError catchError;
    private final DataSource dataSource;

    @Autowired
    public CustomHealthCheck(CatchError catchError, DataSource dataSource) {
        this.catchError = catchError;
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {

        boolean databaseIsUp = checkDatabaseConnection();
        Health databaseUp = Health.up().withDetail("database", "up").build();
        Health databaseDown = Health.down().withDetail("database", "down").build();

        return databaseIsUp ? databaseUp : databaseDown;
    }

    private boolean checkDatabaseConnection() {

        return catchError.run(() -> {
            Connection connection = dataSource.getConnection();
            return connection != null && !connection.isClosed();
        });
    }
}


