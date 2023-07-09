package base;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



public class SpacePlaceTest {
	// Testing contructor - SpacePlace
    @Test
    public void testSpacePlaceConst() {
        SpacePlace spacePlace = new SpacePlace();

        assertEquals(spacePlace.getxOrg(), 0);
        assertEquals(spacePlace.getyOrg(), 0);
    }
    // Testing constructor with set values
    @Test
    public void testConstWithValues() {
        double theta = 1.5;
        double phi = 2.0;
        SpacePlace spacePlace = new SpacePlace(theta, phi);

        assertEquals(spacePlace.getTheta(), theta, 0.001);
        assertEquals(spacePlace.getPhi(), phi, 0.001);
    }
    // Testing setters and getters
    @Test 
    public void testSettersAndGetters() {
        SpacePlace spacePlace = new SpacePlace();

        int xOrg = 10;
        int yOrg = -5;
        double theta = 2.7;
        double phi = 1.2;

        spacePlace.setxOrg(xOrg);
        spacePlace.setyOrg(yOrg);
        spacePlace.setTheta(theta);
        spacePlace.setPhi(phi);

        assertEquals(spacePlace.getxOrg(), xOrg);
        assertEquals(spacePlace.getyOrg(), yOrg);
        assertEquals(spacePlace.getTheta(), theta, 0.001);
        assertEquals(spacePlace.getPhi(), phi, 0.001);
    }
}
