package dev.pater.testcontainersexample;

import org.springframework.jdbc.core.JdbcTemplate;

public class ExampleService {

    private final JdbcTemplate jdbcTemplate;

    public ExampleService(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public int count(){
        return this.jdbcTemplate
                .queryForObject("SELECT count(*) as sum FROM USERS",
                (resultSet, i) -> resultSet.getInt("sum"));
    }

    public void save(String firstName) {
        this.jdbcTemplate.update("INSERT INTO USERS(first_name) VALUES (?)", firstName);
    }
}
