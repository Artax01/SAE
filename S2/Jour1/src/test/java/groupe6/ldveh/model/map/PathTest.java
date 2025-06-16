package groupe6.ldveh.model.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PathTest {

    @Test
    void testPathInitialization() {
        Path path = new Path(1, 2, 10);

        assertEquals(1, path.getFirstPlace());
        assertEquals(2, path.getSecondePlace());
        assertEquals(10, path.getLength());
    }
}