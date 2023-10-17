package org.example.source.database;

import org.example.source.models.Player;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;


class DatabaseHandlerTest {

    @Test
    public void testSignUpPlayer() {
        try (GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("postgres:latest"))
                .withEnv("POSTGRES_USER", "test")
                .withEnv("POSTGRES_PASSWORD", "test")
                .withExposedPorts(5432)) {

            container.start();

            DatabaseHandler databaseHandler = new DatabaseHandler();
            Player player = new Player("test", "test");
            databaseHandler.signUpPlayer(player);

            container.stop();
        }
    }

    @Test
    public void testLoginPlayer() {
        try (GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("postgres:latest"))
                .withEnv("POSTGRES_USER", "test")
                .withEnv("POSTGRES_PASSWORD", "test")
                .withExposedPorts(5432)) {

            DatabaseHandler databaseHandler = new DatabaseHandler();
            Player player = databaseHandler.loginPlayer("test", "test");
            assertNotNull(player);
            assertEquals("test", player.getUsername());
            assertEquals("test", player.getPassword());
        }
    }

}