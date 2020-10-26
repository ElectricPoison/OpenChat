package openchat.core;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

	public static TrayIcon trayIcon;
	public static OpenChat openChat = new OpenChat();
	
	public static void main(String[] args) {
		new Main().openProgram();
	}
	
	public void openProgram()
	{
		openChat.main();

		trayIcon = null;
		if (SystemTray.isSupported()) {
		    // get the SystemTray instance
		    SystemTray tray = SystemTray.getSystemTray();
		    // load an image
		    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon/chat-2-icon (1).png"));
		    // create a action listener to listen for default action executed on the tray icon
		    ActionListener listener = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            System.exit(0);
		        }
		    };
		    
		    ActionListener listener2 = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	openChat.setVisible(true);
		        }
		    };
		    // create a popup menu
		    PopupMenu popup = new PopupMenu();
		    // create menu item for the default action
		    MenuItem defaultItem = new MenuItem("Quit");
		    defaultItem.addActionListener(listener);
		    
		    MenuItem openItem = new MenuItem("Open");
		    openItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					openChat.setVisible(true);
				}
			});
		    
		    
		    popup.add(openItem);
		    popup.add(defaultItem);
		    /// ... add other items
		    // construct a TrayIcon
		    trayIcon = new TrayIcon(image, "OpenChat", popup);
		    // set the TrayIcon properties
		    trayIcon.addActionListener(listener2);
		    // ...
		    // add the tray image
		    try {
		        tray.add(trayIcon);
		    } catch (AWTException e) {
		        System.err.println(e);
		    }
		  }
	}
	
}
