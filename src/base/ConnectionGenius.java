package base;

import java.net.InetAddress;

/**
 * @author Kevan Buckley, maintained by Szymon Baraniecki student 1913153
 * @version 2.0, 2014
 */
//Java class 'ConnectionGenius'
public class ConnectionGenius {
	// InetAddress class used to determine IP address assigned to variable 'ipa'
	InetAddress ipa;

	public ConnectionGenius(InetAddress ipa) {
		this.ipa = ipa;
	}
	// 'fireUpGame' Method used to initialise game start up process
	public void fireUpGame() {
		downloadWebVersion(); // Used to download web version of the game - Including assets/resources etc.
		connectToWebService(); // Used to establish a connection with a online server.
		awayWeGo(); // Used to start the game
	}
	// Outputs generated whilst 'downloadWebVersion' method is started
	public void downloadWebVersion() {
		System.out.println("Getting specialised web version.");
		System.out.println("Wait a couple of moments");
	}
	// Output generated whilst 'connectToWebService' method is started
	public void connectToWebService() {
		System.out.println("Connecting");
	}
	// Output generated whilst 'awayWeGo' method is started
	public void awayWeGo() {
		System.out.println("Ready to play");
	}

}
