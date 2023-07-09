package base;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import org.junit.jupiter.api.Test;


public class PictureFrameTest {
	// Testing for PictureFrame preferred size
    @Test
    public void testGetPreferredSize() {
        PictureFrame pictureFrame = new PictureFrame();
        PictureFrame.DominoPanel dominoPanel = pictureFrame.new DominoPanel();
        Dimension expectedDimension = new Dimension(202, 182);
        assertEquals(expectedDimension, dominoPanel.getPreferredSize());
    }
    //
    @Test
    public void testDrawGrid() {
    	
    }
    //
    @Test
    public void testDrawHeadings() {

    }
    //
    @Test
    public void testDrawDomino() {

    }
    
}
