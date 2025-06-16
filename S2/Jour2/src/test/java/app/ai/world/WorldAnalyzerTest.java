package app.ai.world;

import app.Main;
import app.model.map.World;
import app.model.parser.WorldIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldAnalyzerTest {
    World w;
    WorldAnalyzer analyzer;

    @BeforeEach
    public void setUp() throws Exception {
        w = WorldIO.loadWorld(Main.class.getResourceAsStream("Monde1.json"));
        analyzer = new WorldAnalyzer(w);
    }

    @Test
    public void testConnex() {
        assertTrue(analyzer.isConnexe());
    }

    @Test
    public void testLessDistanceToReach() {
        assertEquals(12, analyzer.lessDistanceToReach(w.getPlaceFromId(1), w.getPlaceFromId(6)));
    }
}
