package connect;



import java.io.*;
import java.net.*;
import java.util.*;



public class SocketServer {
	
	private static int ipqcType = 0;
	private static int railWayType = 1;
	
	private static int alarmOneLeval = 20;
	private static int alarmTwoLeval = 80;
	private static int alarmThreeLeval = 150;
	private static connectDB con = null;
	private static Socket socket = null;
	public static void main(String[] args){
		
		int port=9090;
		con = new connectDB();
		con.loadSqlData();
		try{
			ServerSocket server = new ServerSocket(port);
			while(true){
				socket = server.accept();
				
				new Thread(new Runnable(){
					
					@Override
					public void run(){
						// TODO Auto-generated method stub
						try{
							// ��������
							InputStream is = socket.getInputStream();
							DataInputStream dis = new DataInputStream(is);
							
							
							int client_type = dis.readInt();							
							String client_number = dis.readUTF();	
							
							
							long time = dis.readLong();
							double latitude = dis.readDouble();
							double longitude = dis.readDouble();

								
							getAlarmInfo(client_type,client_number,latitude,longitude);
							
							dis.close();
							is.close();
//							socket.shutdownInput();
							socket.close();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void judgelogin(int client_type,String client_number){
		try{
			// �������
			System.out.println("client_type: " + client_type + "  client_number: " + client_number);
			
			
			// ��������
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			// ��ȡ�빤������Ļ𳵾���
			boolean judge = con.judgeLogin(client_type, client_number);
			if(judge == true){
				dos.writeInt(11);
			}
			else{
				dos.writeInt(0);
			}
			os.flush();
			dos.close();
			os.close();
		}catch(Exception e){
			e.printStackTrace();
		}				
	}
	
	
	public static void getAlarmInfo(int client_type,String client_number,double longitude,double latitude){
		try{
			// �������
			System.out.println("client_type: " + client_type + "  client_number: " + client_number + "  longitude=" + longitude + "  latitude=" + latitude);
			
			// �����ݲ������ݿ�
			con.insertLocal(client_type, client_number, latitude, longitude);
			
			// ��������
			if(client_type == ipqcType) // �����ǹ���
			{
				// ��������
				OutputStream os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);

				// ��ȡ�빤������Ļ𳵾���
				double distance = con.judgeDistance(latitude, longitude);
				/*
				 * û�о�����0
				 * һ��������1
				 * ����������2
				 * ����������3
				 * һ��������Σ��
				 */
				if(distance < alarmOneLeval){
					System.out.println("����һ��Ԥ��");
					dos.writeInt(1);
				}else if(distance < alarmTwoLeval){
					System.out.println("���ض���Ԥ��");
					dos.writeInt(2);
				}else if(distance < alarmThreeLeval){
					System.out.println("��������Ԥ��");
					dos.writeInt(3);
				}else {
					System.out.println("û��Σ��");
					dos.writeInt(0);
				}
				os.flush();
				dos.close();
				os.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
