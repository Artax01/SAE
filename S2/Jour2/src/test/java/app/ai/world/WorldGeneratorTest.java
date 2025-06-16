package app.ai.world;

import app.model.entity.Player;
import app.model.map.Place;
import app.model.map.World;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the {@link WorldGenerator} class.
 * These tests verify the correct functionality of the {@link WorldGenerator#createWorld} method.
 */
public class WorldGeneratorTest {

    private final Player player = new Player(100, 100, 10, 20, 100, 100);

    /**
     * Tests the creation of a world with valid parameters.
     * Verifies that the world is created without throwing an exception.
     * @throws Exception 
     */
    @Test
    void ValidParameters() throws Exception {
        World world = WorldGenerator.createWorld("Test World", 5, player, 0.2f, 0.2f, 0.2f, 0.5f);
        assertNotNull(world, "The world should not be null.");
    }

    /**
     * Tests the creation of a world with fewer than 2 places.
     * Verifies that the method throws an {@link IllegalArgumentException}.
     */
    @Test
    void TooFewPlaces() {
        assertThrows(IllegalArgumentException.class, () ->
            WorldGenerator.createWorld("Test World", 1, player, 0.2f, 0.2f, 0.2f, 0.5f),
            "nbPlace must be at least 2");
    }

    /**
     * Tests the creation of a world with an invalid sum of percentages.
     * Verifies that the method throws an {@link IllegalArgumentException}.
     */
    @Test
    void InvalidPercentageSum() {
        assertThrows(IllegalArgumentException.class, () ->
            WorldGenerator.createWorld("Test World", 5, player, 0.5f, 0.5f, 0.5f, 0.5f),
            "The sum of percentageEndPoint, percentageStartPoint, and percentageDefeatPoint must be between 0 and 1");
    }

    /**
     * Tests the creation of a world with a start point percentage greater than 1.
     * Verifies that the method throws an {@link IllegalArgumentException}.
     */
    @Test
    void StartPointPercentageTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
            WorldGenerator.createWorld("Test World", 5, player, 0.2f, 1.2f, 0.2f, 0.5f),
            "percentageStartPoint must be between 0 and 1");
    }

    /**
     * Tests the creation of a world with a path coverage percentage greater than 1.
     * Verifies that the method throws an {@link IllegalArgumentException}.
     */
    @Test
    void CoveragePercentageTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
            WorldGenerator.createWorld("Test World", 5, player, 0.2f, 0.2f, 0.2f, 1.2f),
            "percentageCoverage must be between 0 and 1");
    }

    /**
     * Tests the creation of a world with a defeat point percentage greater than 1.
     * Verifies that the method throws an {@link IllegalArgumentException}.
     */
    @Test
    void DefeatPointPercentageTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
            WorldGenerator.createWorld("Test World", 5, player, 0.2f, 0.2f, 1.2f, 0.5f),
            "percentageDefeatPoint must be between 0 and 1");
    }

    /**
     * Tests the creation of a world with a null player.
     * Verifies that the method throws an {@link IllegalArgumentException}.
     */
    @Test
    void NullPlayer() {
        assertThrows(IllegalArgumentException.class, () ->
            WorldGenerator.createWorld("Test World", 5, null, 0.2f, 0.2f, 0.2f, 0.5f),
            "The player cannot be null");
    }

    /**
     * Tests the creation of a world with valid start and end points.
     * Verifies that the world contains at least one start point and one end point.
     * @throws Exception 
     */
    @Test
    void ValidStartAndEndPoints() throws Exception {
        World world = WorldGenerator.createWorld("Test World", 5, player, 0.2f, 0.2f, 0.2f, 0.5f);
        assertTrue(world.getPlaces().stream().anyMatch(Place::isStart), "The world must contain at least one start point.");
        assertTrue(world.getPlaces().stream().anyMatch(Place::isEnd), "The world must contain at least one end point.");
    }

    /**
     * Tests the creation of a world with valid defeat points.
     * Verifies that the world contains at least one defeat point.
     * @throws Exception 
     */
    @Test
    void ValidDefeatPoints() throws Exception {
        World world = WorldGenerator.createWorld("Test World", 5, player, 0.2f, 0.2f, 0.2f, 0.5f);
        assertTrue(world.getPlaces().stream().anyMatch(Place::isDefeat), "The world must contain at least one defeat point.");
    }

    /**
     * Tests the creation of a world with 0% path coverage.
     * Verifies that the world contains no paths.
     * @throws Exception 
     */
    @Test
    void NoPaths() throws Exception {
        World world = WorldGenerator.createWorld("Test World", 5, player, 0.2f, 0.2f, 0.2f, 0f);
        assertTrue(world.getPaths().isEmpty(), "The world should contain no paths.");
    }
}