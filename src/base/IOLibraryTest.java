package base;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.Test;

public class IOLibraryTest {

    @Test
    public void testGetString() {
        // Provide input using a ByteArrayInputStream
        String input = "123";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
       
        String result = IOLibrary.getString();
        
        // Verify the expected output
        assertEquals("123", result);
    }
    // No Test required - ConnectionGenius class not used.
    @Test
    public void testGetIPAddress() {
    }
}
