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

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static TrayIcon trayIcon;
	public static OpenChat openChat = new OpenChat();
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		new Main().openProgram();
	}
	
	public void openProgram()
	{
		openChat.main();

		trayIcon = null;
		if (SystemTray.isSupported()) {
		    SystemTray tray = SystemTray.getSystemTray();
		    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon/chat-2-icon (1).png"));
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
		    PopupMenu popup = new PopupMenu();
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
		    trayIcon = new TrayIcon(image, "OpenChat", popup);
		    trayIcon.addActionListener(listener2);
		    try {
		        tray.add(trayIcon);
		    } catch (AWTException e) {
		        System.err.println(e);
		    }
		  }
	}
	
}
