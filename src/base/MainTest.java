package base;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainTest {
	// Testing generating dominos - if its 28 dominos
    @Test
    void testGenerateDominoes() {
        Main gDomino = new Main();
        gDomino.generateDominoes();
        List<Domino> digits = gDomino._Digits;
        assertEquals(digits.size(), 28); 
    }
    // Testing main menu
    @Test
    public void testPrintMainMenu() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        printMainMenu();

        // Checking for the expected output
        String expectedOutput = "Main menu\n" +
                                "==========\n" +
                                "Main menu\n" +
                                "==========\n" +
                                "1) Play\n" +
                                "2) View high scores\n" +
                                "3) View rules\n" +
                                "5) Get inspiration\n" +
                                "0) Quit\n";
        assertEquals(outputStream.toString(), expectedOutput);
    }

    // Method used to call for MainMenu
    private void printMainMenu() {
        System.out.println();
        String h1 = "Main menu";
        String u1 = h1.replaceAll(".", "=");
        System.out.println(u1);
        System.out.println(h1);
        System.out.println(u1);
        System.out.println("1) Play");
        System.out.println("2) View high scores");
        System.out.println("3) View rules");
        System.out.println("5) Get inspiration");
        System.out.println("0) Quit");
    }
}