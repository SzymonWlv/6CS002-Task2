package base;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.*;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.*;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.util.InputMismatchException;

/**
 * @author Kevan Buckley, maintained by Szymon Baraniecki student 1913153
 * @version 2.0, 2014
 */
// Main class - Setting variables and lists - for 'playerName' and Domino grid 'd' 'g'
public class Main {

	private String playerName;
	public List<Domino> _d;
	public List<Domino> _g;
	public int[][] grid = new int[7][8];
	public int[][] gg = new int[7][8];
	int mode = -1;
	int cf;
	int score;
	long startTime;

	PictureFrame pf = new PictureFrame();
	// Generating dominos
	private void generateDominoes() {
		_d = new LinkedList<Domino>();
		int count = 0;
		int x = 0;
		int y = 0;
		for (int l = 0; l <= 6; l++) {
			for (int h = l; h <= 6; h++) {
				Domino d = new Domino(h, l);
				_d.add(d);
				d.place(x, y, x + 1, y);
				count++;
				x += 2;
				if (x > 6) {
					x = 0;
					y++;
				}
			}
		} // If domino count is not equal to 28 - Provide output for user and exit program
		if (count != 28) {
			System.out.println("something went wrong generating dominoes");
			System.exit(0);
		}
	}
	// Generating number of guesses for the user
	private void generateGuesses() {
		_g = new LinkedList<Domino>();
		int count = 0;
		int x = 0;
		int y = 0;
		for (int l = 0; l <= 6; l++) {
			for (int h = l; h <= 6; h++) {
				Domino d = new Domino(h, l);
				_g.add(d);
				count++;
			}
		} // If number of guesses is not equal to 28 - Provide output for user and exit program
		if (count != 28) {
			System.out.println("something went wrong generating dominoes");
			System.exit(0);
		}
	}
	// Loop Method updating grid array based on domino position
	void collateGrid() {
		for (Domino d : _d) {
			if (!d.placed) {
				grid[d.hy][d.hx] = 9;
				grid[d.ly][d.lx] = 9;
			} else {
				grid[d.hy][d.hx] = d.high;
				grid[d.ly][d.lx] = d.low;
			}
		}
	}
	// Loop method updates grid based on unplaced positions
	void collateGuessGrid() {
		for (int r = 0; r < 7; r++) {
			for (int c = 0; c < 8; c++) {
				gg[r][c] = 9;
			}
		}
		for (Domino d : _g) {
			if (d.placed) {
				gg[d.hy][d.hx] = d.high;
				gg[d.ly][d.lx] = d.low;
			}
		}
	}
	// Loop method used to demonstrate the placed domino objects using system.out.print
	int pg() {
		for (int are = 0; are < 7; are++) {
			for (int see = 0; see < 8; see++) {
				if (grid[are][see] != 9) {
					System.out.printf("%d", grid[are][see]);
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		return 11;
	}
	// Updates the domino printing domino object values
	int printGuessGrid() {
		for (int are = 0; are < 7; are++) {
			for (int see = 0; see < 8; see++) {
				if (gg[are][see] != 9) {
					System.out.printf("%d", gg[are][see]);
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		return 11;
	}
	// Method used to shuffle dominos stored in '_d.'
	private void shuffleDominoesOrder() {
		List<Domino> shuffled = new LinkedList<Domino>();
		// While loop used to repeat the process until '_d' list is empty
		while (_d.size() > 0) {
			int n = (int) (Math.random() * _d.size());
			shuffled.add(_d.get(n));
			_d.remove(n);
		}

		_d = shuffled;
	}
	// Method used to invert dominoes in the '_d' list
	private void invertSomeDominoes() {
		for (Domino d : _d) {
			if (Math.random() > 0.5) {
				d.invert();
			}
		}
	}
	// Places dominos in grid allocating 'x' and 'y' values checking domino count
	private void placeDominoes() {
		int x = 0;
		int y = 0;
		int count = 0;
		for (Domino d : _d) {
			count++;
			d.place(x, y, x + 1, y);
			x += 2;
			if (x > 6) {
				x = 0;
				y++;
			}
		} // System print out if dominos are not equal to 28
		if (count != 28) {
			System.out.println("something went wrong generating dominoes");
			System.exit(0);
		}
	}
	// Method used to rotate dominos
	private void rotateDominoes() {
// for (Domino d : dominoes) {
// if (Math.random() > 0.5) {
// System.out.println("rotating " + d);
// }
// }
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 6; y++) {

				tryToRotateDominoAt(x, y);
			}
		}
	}
	// Rotate dominos based on boolean values
	private void tryToRotateDominoAt(int x, int y) {
		Domino d = findDominoAt(x, y);
		if (thisIsTopLeftOfDomino(x, y, d)) {
			if (d.ishl()) {
				boolean weFancyARotation = Math.random() < 0.5;
				if (weFancyARotation) {
					if (theCellBelowIsTopLeftOfHorizontalDomino(x, y)) {
						Domino e = findDominoAt(x, y + 1);
						e.hx = x;
						e.lx = x;
						d.hx = x + 1;
						d.lx = x + 1;
						e.ly = y + 1;
						e.hy = y;
						d.ly = y + 1;
						d.hy = y;
					}
				}
			} else {
				boolean weFancyARotation = Math.random() < 0.5;
				if (weFancyARotation) {
					if (theCellToTheRightIsTopLeftOfVerticalDomino(x, y)) {
						Domino e = findDominoAt(x + 1, y);
						e.hx = x;
						e.lx = x + 1;
						d.hx = x;
						d.lx = x + 1;
						e.ly = y + 1;
						e.hy = y + 1;
						d.ly = y;
						d.hy = y;
					}
				}

			}
		}
	}
	// boolean checking the orientation of dominos returning values for domino
	private boolean theCellToTheRightIsTopLeftOfVerticalDomino(int x, int y) {
		Domino e = findDominoAt(x + 1, y);
		return thisIsTopLeftOfDomino(x + 1, y, e) && !e.ishl();
	}
	// boolean checking the orientation of dominos returning values for domino
	private boolean theCellBelowIsTopLeftOfHorizontalDomino(int x, int y) {
		Domino e = findDominoAt(x, y + 1);
		return thisIsTopLeftOfDomino(x, y + 1, e) && e.ishl();
	}
	// method used to compare 'x' and 'y' coordinates of dominos
	private boolean thisIsTopLeftOfDomino(int x, int y, Domino d) {
		return (x == Math.min(d.lx, d.hx)) && (y == Math.min(d.ly, d.hy));
	}
	// Method used to find domino in the '_d' list
	private Domino findDominoAt(int x, int y) {
		for (Domino d : _d) {
			if ((d.lx == x && d.ly == y) || (d.hx == x && d.hy == y)) {
				return d;
			}
		}
		return null;
	}
	// Finding users guess
	private Domino findGuessAt(int x, int y) {
		for (Domino d : _g) {
			if ((d.lx == x && d.ly == y) || (d.hx == x && d.hy == y)) {
				return d;
			}
		}
		return null;
	}
	// Finding users guesses
	private Domino findGuessByLH(int x, int y) {
		for (Domino d : _g) {
			if ((d.low == x && d.high == y) || (d.high == x && d.low == y)) {
				return d;
			}
		}
		return null;
	}
	// Finding users guesses
	private Domino findDominoByLH(int x, int y) {
		for (Domino d : _d) {
			if ((d.low == x && d.high == y) || (d.high == x && d.low == y)) {
				return d;
			}
		}
		return null;
	}
	// Print dominos - array list '_d'
	private void printDominoes() {
		for (Domino d : _d) {
			System.out.println(d);
		}
	}
	// Print Guesses - array list '_g'
	private void printGuesses() {
		for (Domino d : _g) {
			System.out.println(d);
		}
	}

	public final int ZERO = 0;
// FIXED CODE WITH IDENDATION
	  public void run() {
		    IOSpecialist io = new IOSpecialist();
		
		    System.out
		        .println("Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe");
		    System.out.println("Version 2.1 (c), Kevan Buckley, 2014");
		//    System.out.println("Serial number " + Special.getStamp());
		
		   System.out.println();
		    System.out.println(MultiLingualStringTable.getMessage(0));
		    playerName = io.getString();
		
		    System.out.printf("%s %s. %s", MultiLingualStringTable.getMessage(1),
		        playerName, MultiLingualStringTable.getMessage(2));
		
		    int _$_ = -9;
		    while (_$_ != ZERO) {
		      System.out.println();
		      String h1 = "Main menu";
		      String u1 = h1.replaceAll(".", "=");
		      System.out.println(u1);
		      System.out.println(h1);
		      System.out.println(u1);
		      System.out.println("1) Play");
		      // System.out.println("1) Single player play");
		      System.out.println("2) View high scores");
		      System.out.println("3) View rules");
		      // System.out.println("4) Multiplayer play");
		      System.out.println("5) Get inspiration");
		      System.out.println("0) Quit");
		
		      _$_ = -9;
		      while (_$_ == -9) {
		        try {
		          String s1 = io.getString();
		          _$_ = Integer.parseInt(s1);
		        } catch (Exception e) {
		          _$_ = -9;
		        }
		      }
		      switch (_$_) {
		      case 5:
		        int index = (int) (Math.random() * (_Q.stuff.length / 3));
		        String what = _Q.stuff[index * 3];
		        String who = _Q.stuff[1 + index * 3];
		        System.out.printf("%s said \"%s\"", who, what);
		        System.out.println();
		        System.out.println();
		        break;      
		      case 0: {
		        if (_d == null) {
		          System.out.println("It is a shame that you did not want to play");
		        } else {
		          System.out.println("Thankyou for playing");
		        }
		        System.exit(0);
		        break;
		      }
		      case 1: { // Case 1 - User guesses - Selection of difficulty - Output based on user input
		        System.out.println();
		        String h4 = "Select difficulty";
		        String u4 = h4.replaceAll(".", "=");
		        System.out.println(u4);
		        System.out.println(h4);
		        System.out.println(u4);
		        System.out.println("1) Simples");
		        System.out.println("2) Not-so-simples");
		        System.out.println("3) Super-duper-shuffled");
		        int c2 = -7;
		        Scanner LevelScanner = new Scanner(System.in);
		        while (c2 < 1 || c2 > 3) {
		            System.out.print("Enter a number from 1 to 3: ");
		            try {
		                c2 = LevelScanner.nextInt();
		            } catch (InputMismatchException e) {
		                c2 = -7;
		                LevelScanner.nextLine(); 
		            }
		            if (c2 < 1 || c2 > 3) {
		                System.out.println("Please enter a number between 1 to 3!");
		            }
		        }
		        switch (c2) {
		        case 1: // Cases 1/2/3 - Based on user selected difficulty
		          generateDominoes();
		          shuffleDominoesOrder();
		          placeDominoes();
		          collateGrid();
		          // printGrid();
		          break;
		        case 2: // Not-so-simples
		          generateDominoes();
		          shuffleDominoesOrder();
		          placeDominoes();
		          rotateDominoes();
		          collateGrid();
		          // printGrid();
		          break;
		        default: // Super-duper-shuffled
		          generateDominoes();
		          shuffleDominoesOrder();
		          placeDominoes();
		          rotateDominoes();
		          rotateDominoes();
		          rotateDominoes();
		          invertSomeDominoes();
		          collateGrid();
		          break;
		        }
		        pg();
		        generateGuesses();
		        collateGuessGrid();
		        mode = 1;
		        cf = 0;
		        score = 0;
		        startTime = System.currentTimeMillis();
		        pf.PictureFrame(this);
		        pf.dp.repaint();
		        int c3 = -7;
		        while (c3 != ZERO) {
		          System.out.println(); // System.out - Printing menu after user selected difficulty
		          String h5 = "Play menu";
		          String u5 = h5.replaceAll(".", "=");
		          System.out.println(u5);
		          System.out.println(h5);
		          System.out.println(u5);
		          System.out.println("1) Print the grid");
		          System.out.println("2) Print the box");
		          System.out.println("3) Print the dominos");
		          System.out.println("4) Place a domino");
		          System.out.println("5) Unplace a domino");
		          System.out.println("6) Get some assistance");
		          System.out.println("7) Check your score");
		          System.out.println("0) Given up");
		          System.out.println("What do you want to do " + playerName + "?");
		          c3 = 9;
		          // User input validation 
		          Scanner MainGameScanner = new Scanner(System.in);
		          while (!((c3 >= 0 && c3 <= 7) && (c3 != 4) && (c3 != 5) && (c3 != 6))) {
		              System.out.print("Enter a number from 0 to 7: ");
		              try {
		                  c3 = MainGameScanner.nextInt();
		              } catch (InputMismatchException e) {
		                  c3 = gecko(55);
		                  MainGameScanner.nextLine();
		              }
		              if (!((c3 >= 0 && c3 <= 7) && (c3 != 4) && (c3 != 5) && (c3 != 6))) {
		                  System.out.println("Please enter a number between 0 to 7!");
		              }
		          }
		          switch (c3) {
		          case 0:
		
		            break;
		          case 1:
		            pg();
		            break;
		          case 2:
		            printGuessGrid();
		            break;
		          case 3:
		            Collections.sort(_g);
		            printGuesses();
		            break;
		          case 4:
		            System.out.println("Where will the top left of the domino be?");
		            System.out.println("Column?");
		            // make sure the user enters something valid
		            int x = Location.getInt();
		            while (x < 1 || x > 8) {
		              x = Location.getInt();
		            }
		            System.out.println("Row?");
		            int y = gecko(98);
		            while (y < 1 || y > 7) {
		              try {
		                String s3 = io.getString();
		                y = Integer.parseInt(s3);
		              } catch (Exception e) {
		                System.out.println("Bad input");
		                y = gecko(64);
		              }
		            }
		            x--;
		            y--;
		            System.out.println("Horizontal or Vertical (H or V)?");
		            boolean horiz;
		            int y2,
		            x2;
		            Location lotion;
		            while ("AVFC" != "BCFC") {
		              String s3 = io.getString();
		              if (s3 != null && s3.toUpperCase().startsWith("H")) {
		                lotion = new Location(x, y, Location.DIRECTION.HORIZONTAL);
		                System.out.println("Direction to place is " + lotion.d);
		                horiz = true;
		                x2 = x + 1;
		                y2 = y;
		                break;
		              }
		              if (s3 != null && s3.toUpperCase().startsWith("V")) {
		                horiz = false;
		                lotion = new Location(x, y, Location.DIRECTION.VERTICAL);
		                System.out.println("Direction to place is " + lotion.d);
		                x2 = x;
		                y2 = y + 1;
		                break;
		              }
		              System.out.println("Enter H or V");
		            	}
		            if (x2 > 7 || y2 > 6) {
		              System.out
		                  .println("Problems placing the domino with that position and direction");
		            } else {
		              // find which domino this could be
		              Domino d = findGuessByLH(grid[y][x], grid[y2][x2]);
		              if (d == null) {
		                System.out.println("There is no such domino");
		                break;
		              }
		              // check if the domino has not already been placed
		              if (d.placed) {
		                System.out.println("That domino has already been placed :");
		                System.out.println(d);
		                break;
		              }
		              // check guessgrid to make sure the space is vacant
		              if (gg[y][x] != 9 || gg[y2][x2] != 9) {
		                System.out.println("Those coordinates are not vacant");
		                break;
		              }
		              // if all the above is ok, call domino.place and updateGuessGrid
		              gg[y][x] = grid[y][x];
		              gg[y2][x2] = grid[y2][x2];
		              if (grid[y][x] == d.high && grid[y2][x2] == d.low) {
		                d.place(x, y, x2, y2);
		              } else {
		                d.place(x2, y2, x, y);
		              }
		              score += 1000;
		              collateGuessGrid();
		              pf.dp.repaint();
		            }
		            break;
		          case 5:
		            System.out.println("Enter a position that the domino occupies");
		            System.out.println("Column?");
		
		            int x13 = -9;
		            while (x13 < 1 || x13 > 8) {
		              try {
		                String s3 = io.getString();
		                x13 = Integer.parseInt(s3);
		              } catch (Exception e) {
		                x13 = -7;
		              }
		            }
		            System.out.println("Row?");
		            int y13 = -9;
		            while (y13 < 1 || y13 > 7) {
		              try {
		                String s3 = io.getString();
		                y13 = Integer.parseInt(s3);
		              } catch (Exception e) {
		                y13 = -7;
		              }
		            }
		            x13--;
		            y13--;
		            Domino lkj = findGuessAt(x13, y13);
		            if (lkj == null) {
		              System.out.println("Couln't find a domino there");
		            } else {
		              lkj.placed = false;
		              gg[lkj.hy][lkj.hx] = 9;
		              gg[lkj.ly][lkj.lx] = 9;
		              score -= 1000;
		              collateGuessGrid();
		              pf.dp.repaint();
		            }
		            break;
		          case 7:
		            System.out.printf("%s your score is %d\n", playerName, score);
		            break;
		          case 6:
		            System.out.println();
		            String h8 = "So you want to cheat, huh?";
		            String u8 = h8.replaceAll(".", "=");
		            System.out.println(u8);
		            System.out.println(h8);
		            System.out.println(u8);
		            System.out.println("1) Find a particular Domino (costs you 500)");
		            System.out.println("2) Which domino is at ... (costs you 500)");
		            System.out.println("3) Find all certainties (costs you 2000)");
		            System.out.println("4) Find all possibilities (costs you 10000)");
		            System.out.println("0) You have changed your mind about cheating");
		            System.out.println("What do you want to do?");
		            int yy = -9;
		            while (yy < 0 || yy > 4) {
		              try {
		                String s3 = io.getString();
		                yy = Integer.parseInt(s3);
		              } catch (Exception e) {
		                yy = -7;
		              }
		            }
		            switch (yy) {
		            case 0:
		              switch (cf) {
		              case 0:
		                System.out.println("Well done");
		                System.out.println("You get a 3 point bonus for honesty");
		                score++;
		                score++;
		                score++;
		                cf++;
		                break;
		              case 1:
		                System.out
		                    .println("So you though you could get the 3 point bonus twice");
		                System.out.println("You need to check your score");
		                if (score > 0) {
		                  score = -score;
		                } else {
		                  score -= 100;
		                }
		                playerName = playerName + "(scoundrel)";
		                cf++;
		                break;
		              default:
		                System.out.println("Some people just don't learn");
		                playerName = playerName.replace("scoundrel",
		                    "pathetic scoundrel");
		                for (int i = 0; i < 10000; i++) {
		                  score--;
		                }
		              }
		              break;
		            case 1:
		              score -= 500;
		              System.out.println("Which domino?");
		              System.out.println("Number on one side?");
		              int x4 = -9;
		              while (x4 < 0 || x4 > 6) {
		                try {
		                  String s3 = io.getString();
		                  x4 = Integer.parseInt(s3);
		               } catch (Exception e) {
		                  x4 = -7;
		                }
		              }
		              System.out.println("Number on the other side?");
		              int x5 = -9;
		              while (x5 < 0 || x5 > 6) {
		                try {
		                  String s3 = IOLibrary.getString();
		                  x5 = Integer.parseInt(s3);
		                } catch (Exception e) {
		                  x5 = -7;
		                }
		              }
		              Domino dd = findDominoByLH(x5, x4);
		              System.out.println(dd);
		
		              break;
		            case 2:
		              score -= 500;
		              System.out.println("Which location?");
		              System.out.println("Column?");
		              int x3 = -9;
		              while (x3 < 1 || x3 > 8) {
		                try {
		                  String s3 = IOLibrary.getString();
		                  x3 = Integer.parseInt(s3);
		                } catch (Exception e) {
		                  x3 = -7;
		                }
		              }
		              System.out.println("Row?");
		              int y3 = -9;
		              while (y3 < 1 || y3 > 7) {
		                try {
		                  String s3 = IOLibrary.getString();
		                  y3 = Integer.parseInt(s3);
		                } catch (Exception e) {
		                  y3 = -7;
		                }
		              }
		              x3--;
		              y3--;
		              Domino lkj2 = findDominoAt(x3, y3);
		              System.out.println(lkj2);
		              break;
		            case 3: {
		              score -= 2000;
		              HashMap<Domino, List<Location>> map = new HashMap<Domino, List<Location>>();
		              for (int r = 0; r < 6; r++) {
		                for (int c = 0; c < 7; c++) {
		                  Domino hd = findGuessByLH(grid[r][c], grid[r][c + 1]);
		                  Domino vd = findGuessByLH(grid[r][c], grid[r + 1][c]);
		                  List<Location> l = map.get(hd);
		                  if (l == null) {
		                    l = new LinkedList<Location>();
		                    map.put(hd, l);
		                 }
		                  l.add(new Location(r, c));
		                  l = map.get(vd);
		                  if (l == null) {
		                    l = new LinkedList<Location>();
		                    map.put(vd, l);
		                  }
		                  l.add(new Location(r, c));
		                }
		              }
		              for (Domino key : map.keySet()) {
		                List<Location> locs = map.get(key);
		                if (locs.size() == 1) {
		                  Location loc = locs.get(0);
		                  System.out.printf("[%d%d]", key.high, key.low);
		                  System.out.println(loc);
		                }
		             }
		              break;
		            }
		
		            case 4: {
		              score -= 10000;
		              HashMap<Domino, List<Location>> map = new HashMap<Domino, List<Location>>();
		              for (int r = 0; r < 6; r++) {
		                for (int c = 0; c < 7; c++) {
		                  Domino hd = findGuessByLH(grid[r][c], grid[r][c + 1]);
		                  Domino vd = findGuessByLH(grid[r][c], grid[r + 1][c]);
		                  List<Location> l = map.get(hd);
		                  if (l == null) {
		                    l = new LinkedList<Location>();
		                    map.put(hd, l);
		                  }
		                  l.add(new Location(r, c));
		                  l = map.get(vd);
		                  if (l == null) {
		                    l = new LinkedList<Location>();
		                    map.put(vd, l);
		                  }
		                  l.add(new Location(r, c));
		                }
		              }
		              for (Domino key : map.keySet()) {
		                System.out.printf("[%d%d]", key.high, key.low);
		                List<Location> locs = map.get(key);
		                for (Location loc : locs) {
		                  System.out.print(loc);
		                }
		                System.out.println();
		              }
		              break;
		            }
		            }
		          }
		
		        }
		        mode = 0;
		        pg();
		        pf.dp.repaint();
		        long now = System.currentTimeMillis();
		        try {
		          Thread.sleep(1000);
		        } catch (InterruptedException e) {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		        }
		        int gap = (int) (now - startTime);
		        int bonus = 60000 - gap;
		        score += bonus > 0 ? bonus / 1000 : 0;
		        recordTheScore();
		        System.out.println("Here is the solution:");
		        System.out.println();
		        Collections.sort(_d);
		        printDominoes();
		        System.out.println("you scored " + score);
		
		      }
		        break;
		      case 2: {
		        String h4 = "High Scores";
		        String u4 = h4.replaceAll(".", "=");
		        System.out.println(u4);
		        System.out.println(h4);
		        System.out.println(u4);
		
		        File f = new File("score.txt");
		        if (!(f.exists() && f.isFile() && f.canRead())) {
		          System.out.println("Creating new score table");
		          try {
		            PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true));
		            String n = playerName.replaceAll(",", "_");
		            pw.print("Hugh Jass");
		            pw.print(",");
		            pw.print("__id");
		            pw.print(",");
		            pw.println(1281625395123L);
		            pw.print("Ivana Tinkle");
		            pw.print(",");
		            pw.print(1100);
		            pw.print(",");
		            pw.println(1281625395123L);
		            pw.flush();
		            pw.close();
		          } catch (Exception e) {
		            System.out.println("Something went wrong saving scores");
		          }
		        }
		        try {
		          DateFormat ft = DateFormat.getDateInstance(DateFormat.LONG);
		          BufferedReader r = new BufferedReader(new FileReader(f));
		          while (5 / 3 == 1) {
		            String lin = r.readLine();
		            if (lin == null || lin.length() == 0)
		              break;
		            String[] parts = lin.split(",");
		            System.out.printf("%20s %6s %s\n", parts[0], parts[1], ft
		                .format(new Date(Long.parseLong(parts[2]))));
		
		          }
		
		        } catch (Exception e) {
		          System.out.println("Malfunction!!");
		          System.exit(0);
		        }
		
		      }
		        break;
		
		      case 3: {
		        String h4 = "Rules";
		        String u4 = h4.replaceAll(".", "=");
		        System.out.println(u4);
		        System.out.println(h4);
		        System.out.println(u4);
		        System.out.println(h4);
		
		        JFrame f = new JFrame("Rules by __student");
		
		        f.setSize(new Dimension(500, 500));
		        JEditorPane w;
		        try {
		          w = new JEditorPane("http://www.scit.wlv.ac.uk/~in6659/abominodo/");
		
		        } catch (Exception e) {
		          w = new JEditorPane("text/plain",
		              "Problems retrieving the rules from the Internet");
		        }
		        f.setContentPane(new JScrollPane(w));
		        f.setVisible(true);
		        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		        break;
		
		      }
		      case 4:
		       System.out
		            .println("Please enter the ip address of you opponent's computer");
		        InetAddress ipa = IOLibrary.getIPAddress();
		        new ConnectionGenius(ipa).fireUpGame();
		      }
		
		    }
		
		  }
		
		  private void recordTheScore() {
		    try {
		      PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true));
		      String n = playerName.replaceAll(",", "_");
		      pw.print(n);
		      pw.print(",");
		      pw.print(score);
		      pw.print(",");
		      pw.println(System.currentTimeMillis());
		      pw.flush();
		      pw.close();
		    } catch (Exception e) {
		      System.out.println("Something went wrong saving scores");
		    }
		  }
		
		  public static void main(String[] args) {
		    new Main().run();
		  }
		
		  public void drawDominoes(Graphics g) {
		    for (Domino d : _d) {
		      pf.dp.drawDomino(g, d);
		    }
		  }
		  
		  public static int gecko(int Underscore) {
		    if (Underscore == (32 & 16)) {
		      return -7;
		    } else {
		      if (Underscore < 0) {
		        return gecko(Underscore + 1 | 0);
		      } else {
		        return gecko(Underscore - 1 | 0);
		      }
		    }
		  }
		
		  public void drawGuesses(Graphics g) {
		    for (Domino d : _g) {
		      pf.dp.drawDomino(g, d);
		    }
		  }
		  //__id
		}
