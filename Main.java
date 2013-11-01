package load_test_java;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main 
{

	//rtt(round trip time).means the time it takes from sending a request to the time we receive the reply from server
	static double rtt=0;
	
	// the average of round trip time
	 static double artt;	
	 
	 //number of current threads (Players)
	static int number_of_players;
	
	static int port=7777;
	static String host="localhost";
	public static void main(String[] args) 
	{	

		ExecutorService root;
		boolean run_test=true;
		System.out.println("Main thread ");
		try
			{
				root= Executors.newCachedThreadPool();
				while(!run_test)
				{
					root.execute(new Playerthread(host,port));
				}	
				root.execute(new Playerthread(host,port));
			}catch(Exception e){e.printStackTrace();}

	}
}
	class Playerthread implements Runnable
	{
		DataOutputStream dos;
		DataInputStream dis;
//		ByteArrayOutputStream byteOut;	
//		DataOutputStream bufferedOut;
		

		
		BufferedWriter br;
		Socket connection;
		
		static final int init=0, 
						 join=1, 
						 quit=2, 
						 playerpos=3, 
						 ping=4, 
						itemcollected=5, 
						fire=6, 
						finished=7; 

		
		
		public Playerthread(String host,int port) 
		{
			try
			{
				connection = new Socket(host,port);
				dos= new DataOutputStream(connection.getOutputStream());
				dis= new DataInputStream(connection.getInputStream());
				
				br=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
				
//				byteOut =     new ByteArrayOutputStream();
//				bufferedOut =  new DataOutputStream(byteOut);
				
			}catch(Exception e){e.printStackTrace();}
		}
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			System.out.println("Player "+Thread.currentThread().getId());
			//System.out.printf("Rrtt: %f",10);
			init();
			join();
		}

		public void init()
		{
			try 
			{
				//send '0' init command
				dos.writeByte(0);
				//
				dos.writeInt(Config.CLIENT_VERSION);
				
				//receive 'INIT' from server
				int reply=dis.readInt();
				System.out.println("received Player id:"+reply);
			
				int room=dis.readInt();
				System.out.println("received Player room:"+room);
				System.out.println("received strange:"+dis.read());
				System.out.println("received strange:"+dis.read());
				System.out.println("Game Init OK");
				
			}catch (Exception e) {e.printStackTrace();}
		}
		private void join() 
		{
			// TODO Auto-generated method stub
			try 
			{
				//send the 'join' message
				System.out.println(">>sending 'join' ");
				dos.writeByte(1);

					System.out.println(">> sending 'levelIdWaiting'");
				dos.writeInt(7);

					System.out.println(">> sending 'color'");
				dos.writeInt(1);

				System.out.println(">> sending 'name')");
				/* here is where am stucked.
				*  what variable type to send here so that User.parseInput(...) can receive it.
				*  
				* else if(CLIENT2SERVER[action].param[i] == 's'){ // Read string of predefined constant length
				*	byte[] stringBuffer = new byte[Config.STRING_LENGTH];
				*		vomClient.readFully(stringBuffer, 0, Config.STRING_LENGTH);
				*		erg[i] = stringBuffer;
				*	}

				*/
				
				

			}catch (Exception e) {e.printStackTrace();}
		}
	}



