package base;

import java.awt.*;
import javax.swing.*;

/**
 * @author Kevan Buckley, maintained by Szymon Baraniecki student 1913153
 * @version 2.0, 2014
 */

public class PictureFrame {
	public int[] reroll = null;
	public Main master = null;
	// DominoPanel extends the class JPanel
	class DominoPanel extends JPanel {
		private static final long serialVersionUID = 4190229282411119364L;

		public void drawGrid(Graphics g) {
			for (int are = 0; are < 7; are++) {
				for (int see = 0; see < 8; see++) {
					drawDigitGivenCentre(g, 30 + see * 20, 30 + are * 20, 20, master.grid[are][see]);
				}
			}
		}
		// Method used to draw headings
		public void drawHeadings(Graphics g) {
			for (int are = 0; are < 7; are++) {
				fillDigitGivenCentre(g, 10, 30 + are * 20, 20, are + 1);
			}

			for (int see = 0; see < 8; see++) {
				fillDigitGivenCentre(g, 30 + see * 20, 10, 20, see + 1);
			}
		}
		// Method used to draw the domino, starts when domino is placed 
		public void drawDomino(Graphics g, Domino d) {
			if (d.placed) {
				int y = Math.min(d.ly, d.hy);
				int x = Math.min(d.lx, d.hx);
				int w = Math.abs(d.lx - d.hx) + 1;
				int h = Math.abs(d.ly - d.hy) + 1;
				g.setColor(Color.WHITE);
				g.fillRect(20 + x * 20, 20 + y * 20, w * 20, h * 20);
				g.setColor(Color.RED);
				g.drawRect(20 + x * 20, 20 + y * 20, w * 20, h * 20);
				drawDigitGivenCentre(g, 30 + d.hx * 20, 30 + d.hy * 20, 20, d.high, Color.BLUE);
				drawDigitGivenCentre(g, 30 + d.lx * 20, 30 + d.ly * 20, 20, d.low, Color.BLUE);
			}
		}
		// Code used to set colour 'black' of the object and drawing a digit
		void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
			int radius = diameter / 2;
			g.setColor(Color.BLACK);
			// g.drawOval(x - radius, y - radius, diameter, diameter);
			FontMetrics fm = g.getFontMetrics();
			// Converts string to an integer
			String txt = Integer.toString(n);
			g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
		}
		// Code used to set colour of the object and drawing a digit
		void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n, Color c) {
			int radius = diameter / 2;
			g.setColor(c);
// g.drawOval(x - radius, y - radius, diameter, diameter);
			FontMetrics fm = g.getFontMetrics();
			String txt = Integer.toString(n);
			g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
		}

		void fillDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
			int radius = diameter / 2;
			g.setColor(Color.GREEN);
			g.fillOval(x - radius, y - radius, diameter, diameter);
			g.setColor(Color.BLACK);
			g.drawOval(x - radius, y - radius, diameter, diameter);
			FontMetrics fm = g.getFontMetrics();
			String txt = Integer.toString(n);
			g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
		}

		protected void paintComponent(Graphics g) {
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, getWidth(), getHeight());

		Location l = new Location(1, 2);

		if (master.mode == 1) {
			l.drawGridLines(g);
			drawHeadings(g);
			drawGrid(g);
			master.drawGuesses(g);
		}
		if (master.mode == 0) {
			l.drawGridLines(g);
			drawHeadings(g);
			drawGrid(g);
			master.drawDominoes(g);
		}
	}

		public Dimension getPreferredSize() {
			// Setting the standard view of the domino game to 202 * 182
			return new Dimension(202, 182);
		}
	}

	public DominoPanel dp;
	// Creates a picture frame for the domino
	public void PictureFrame(Main sf) {
		master = sf;
		if (dp == null) {
			JFrame f = new JFrame("Abominodo");
			dp = new DominoPanel();
			f.setContentPane(dp);
			f.pack();
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.setVisible(true);
		}
	}

	public void reset() {
	}

}