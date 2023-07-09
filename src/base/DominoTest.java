package base;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DominoTest {
	//Testing user domino placement
    @Test
    public void testDominoPlacement() {
        Domino domino = new Domino(4, 2);
        domino.place(1, 2, 2, 3);
        assertTrue(domino.placed);
        assertEquals(domino.hx, 1);
        assertEquals(domino.hy, 2);
        assertEquals(domino.lx, 2);
        assertEquals(domino.ly, 3);
    }
    //Testing toString dominos - Unplaced
    @Test
    public void testDominoToString() {
        Domino dominoUnplaced = new Domino(2, 1);
        assertEquals(dominoUnplaced.toString(), "[21]unplaced");
        // Testing domino - placed
        Domino dominoPlace = new Domino(2, 2);
        dominoPlace.place(1, 2, 1, 2);
        assertEquals(dominoPlace.toString(), "[22](2,3)(2,3)");
    }
    // Testing domino inverting
    @Test
    public void testDominoInvert() {
        Domino dominoInvert = new Domino(5, 3);
        dominoInvert.place(2, 3, 4, 5);
        dominoInvert.invert();
        assertEquals(dominoInvert.hx, 4);
        assertEquals(dominoInvert.hy, 5);
        assertEquals(dominoInvert.lx, 2);
        assertEquals(dominoInvert.ly, 3);
    }
}
