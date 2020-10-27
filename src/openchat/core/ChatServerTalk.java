package openchat.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class ChatServerTalk extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public static JLabel txtUserCon;
	
	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServerTalk frame = new ChatServerTalk();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatServerTalk() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBounds(5, 5, 424, 20);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Socket s:ChatServer.__connections) {
					PrintWriter pw = null;
					try {
						pw = new PrintWriter(s.getOutputStream());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pw.println("[Server]: "+e.getActionCommand());
					pw.flush();
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Server Restart");
		btnNewButton.setBounds(5, 25, 103, 38);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Send MOTD");
		btnNewButton_1.setBounds(108, 25, 206, 38);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Server Shutdown");
		btnNewButton_2.setBounds(314, 25, 115, 38);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Users Connected:");
		lblNewLabel.setBounds(5, 67, 103, 14);
		contentPane.add(lblNewLabel);
		
		txtUserCon = new JLabel("0");
		txtUserCon.setBounds(108, 67, 115, 14);
		contentPane.add(txtUserCon);
	}
}
