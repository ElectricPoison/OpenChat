package openchat.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer {

	private ServerSocket __conn;
	private Socket __sock;
	public static ArrayList<Socket> __connections = new ArrayList<Socket>();
	public static int UserCnt = 0;
	private boolean __accepting_connections = false;
	
	public static void main(String[] args) {
		ChatServerTalk.main();
		new ChatServer().StartServer();
	}
	
	public void StartServer() {
		System.out.println("Starting RaidersRunDBAdmin ChatServer");
		
		__accepting_connections = true;
		try {
			__conn = new ServerSocket(31573);
			System.out.println("RRDBACS Started On Port: 31573");
			while(__accepting_connections)
			{
				__sock = __conn.accept();
				System.out.println("Accepting Connection: "+__sock.getLocalPort());
				
				__connections.add(__sock);
				UserCnt++;
				ChatServerTalk.txtUserCon.setText(""+UserCnt);
				new ServerHandle(__sock, __connections).start();
			}
			StopServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void StopServer() throws IOException {
		__conn.close();
		__sock.close();
		for(Socket s:__connections)
		{
			s.close();
		}
	}
	
}
class ServerHandle extends Thread
{
	private Socket __this_connection;
	private ArrayList<Socket> __connections = new ArrayList<Socket>();
	
	private String __resp;
	
	private BufferedReader br;
	private PrintWriter pw;
	
	public ServerHandle(Socket __this_connection, ArrayList<Socket> __connections) {
		this.__this_connection = __this_connection;
		this.__connections = __connections;
		
		try {
			br = new BufferedReader(new InputStreamReader(__this_connection.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			pw = new PrintWriter(__this_connection.getOutputStream());
			pw.println("[Server]: Welcome to the server!<br>");
			pw.flush();
			while((__resp = br.readLine())!=null)
			{
				System.out.println(__resp);
				if(__resp.equals("<<ping"))
				{
					
				}else if(__resp.equals("<<<<close>>>>")) {
					ChatServer.UserCnt --;
					ChatServerTalk.txtUserCon.setText(""+ChatServer.UserCnt);
					System.out.println("closing");
				}
				else {
					for(Socket s : ChatServer.__connections)
					{
						pw = new PrintWriter(s.getOutputStream());
						pw.println(__resp);
						pw.flush();
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Connection Reset");

		}
	}
}