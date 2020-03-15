package dev.pater.testcontainersexample;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;


@Testcontainers
public class ExampleIntegrationTest {
    @Container
    public MySQLContainer mySQLContainer = new MySQLContainer();


    private ExampleService exampleService;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = DataSourceBuilder.create()
                .url(mySQLContainer.getJdbcUrl())
                .password(mySQLContainer.getPassword())
                .username(mySQLContainer.getUsername())
                .driverClassName(mySQLContainer.getDriverClassName())
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE USERS(" +
                "id SERIAL, first_name VARCHAR(255))");

        exampleService = new ExampleService(jdbcTemplate);

    }

    @Test
    public void shouldExistOneUserInDatabaseAfterSave() {

        exampleService.save("EXAMPLE_FIRST_NAME");

        Assert.assertEquals(exampleService.count(), 1);
    }

    @Test
    public void tableWithUsersShouldBeEmpty(){
        Assert.assertEquals(exampleService.count(), 0);
    }
}
