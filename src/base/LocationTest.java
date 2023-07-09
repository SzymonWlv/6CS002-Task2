package base;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void testLocationToStringNoGrid() {
        Location location = new Location(2, 1);
        assertEquals("(2,3)", location.toString());
    }
    //Testing toString with direction set to Vertical
    @Test
    public void testLocationToStringWithDirectionVertical() {
        Location location = new Location(1, 2, Location.DIRECTION.VERTICAL);
        assertEquals(location.toString(), "(3,2,VERTICAL)");
    }
    //Testing toString with direction set to Horizontal
    @Test
    public void testLocationToStringWithDirectionHorizontal() {
        Location location = new Location(2, 2, Location.DIRECTION.HORIZONTAL);
        assertEquals(location.toString(), "(3,3,HORIZONTAL)");
    }
    // Checking user input integer
    @Test
    public void testGetInt() {
        System.setIn(new java.io.ByteArrayInputStream("2\n".getBytes()));
        int result = Location.getInt();
        assertEquals(2, result);
        System.setIn(System.in);
    }
}
