package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class connectDB {
	
	// ����������
	private String driver = "com.mysql.jdbc.Driver";
	
	// URLָ��Ҫ���ʵ����ݿ���gis
	private String url = "jdbc:mysql://localhost:3306/gis?characterEncoding=utf8&useSSL=true";
	
	// Mysql����ʱ�û���
	private String user = "root";
	
	// Mysql����ʱ������
	private String password = "";
	
	// ����Connection����
	private Connection con;
	
	// �洢����Longitude
	private ArrayList<Double> LongitudeList = new ArrayList<Double>(); 
	
	// �洢γ��Latitude
	private ArrayList<Double> LatitudeList = new ArrayList<Double>(); 
	
	// �洢����
	private ArrayList<Double> DistanceList = new ArrayList<Double>();
	
	// �洢�����ݿ����GPS���꣬0άLat,���ȣ�1άLong��γ��
	private double[][] DatabaseGPS = null;
	
	
	// �洢��γ�ȵ����������
	Map<RailwayData, Double> map = new HashMap<RailwayData, Double>();
	
	// �洢ÿ�������µ�λ��
	Map<String,Double[]> mapRailwayLocal = new HashMap<String,Double[]>();
	
	public connectDB(){
		try{
			// ������������
			Class.forName(driver);
			////1.getConnection()����������MySQL���ݿ⣡��
			con = DriverManager.getConnection(url,user,password);
						
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			
		} catch(ClassNotFoundException e) {   
			//���ݿ��������쳣����
			System.out.println("Sorry,can`t find the Driver!");   
			e.printStackTrace();   
		} catch(SQLException e) {
			//���ݿ�����ʧ���쳣����
			e.printStackTrace();  
		} catch (Exception e) {
			// TODO: handle exception
	            e.printStackTrace();
	    } finally{
	    	System.out.println("���ݿ�������ϣ���");
	    }
	}
	
	
	public void loadSqlData(){
		try{
			// ����statement�࣬����ִ��SQL���
			Statement statement = con.createStatement();
			Statement statement_count = con.createStatement();
			// ��ȡ����
			String sql =  "SELECT AVG(KmMark) as KmMark,Latitude,Longitude from xingtanlugps GROUP BY Latitude,Longitude";
			//String sql =  "select * from railwaygps";
			ResultSet rs = statement.executeQuery(sql);
			
			sql = "SELECT count(DISTINCT Latitude,Longitude) as count1 FROM xingtanlugps";
			//sql = "SELECT count(*) as count1 FROM railwaygps";
			ResultSet rs_count = statement_count.executeQuery(sql);
			if(rs_count.next()){
				DatabaseGPS = new double[Integer.valueOf(rs_count.getString("count1"))][2];
			}
			int index = 0;
			while(rs.next()){
				LatitudeList.add(Double.valueOf(rs.getString("Latitude")));
				LongitudeList.add(Double.valueOf(rs.getString("Longitude")));
				DistanceList.add(Double.valueOf(rs.getString("KmMark")));
				
				DatabaseGPS[index][0] = Double.valueOf(rs.getString("Latitude"));
				DatabaseGPS[index][1] = Double.valueOf(rs.getString("Longitude"));
				
//				System.out.println(rs.getString("ID") + "\t" + rs.getString("Longitude") + "\t" + rs.getString("Latitude"));
				++index;
				
			}
			
			System.out.println("·�����ݶ�ȡ���");
			//int a = tree.nearest(DatabaseGPS[0]);
//			System.out.println("KD-tree�������");
			
			

			for(int i = 0; i < LatitudeList.size(); ++i){
				RailwayData temp = new RailwayData(new Double[]{LatitudeList.get(i),LongitudeList.get(i)});
//				RailwayData temp1 = new RailwayData(new Double[]{DatabaseGPS[i][0],DatabaseGPS[i][1]});
//				if(temp.hashCode() != temp1.hashCode())
//					System.out.println("error");
				map.put(temp, DistanceList.get(i));
			}
			System.out.println("HashMap�������");
			
		} catch(SQLException e) {
			//���ݿ�����ʧ���쳣����
			e.printStackTrace();  
		} catch (Exception e) {
			// TODO: handle exception
	            e.printStackTrace();
	    } finally{
	    	System.out.println("���ݿ����ݳɹ���ȡ����");
	    }	
	}
	
	// �жϾ���,��λ����
	public double judgeDistance(double Latitude, double Longitude){
		double result = Integer.MAX_VALUE;
		
		// ��ȡ��ǰ�˵�λ��
		double peopleLocal = findClosedLocal(Latitude,Longitude);
		System.out.println("���˵�ǰλ��:"+peopleLocal);
		
		// ����hashMap���������Ļ�
		for(Map.Entry<String, Double[]> entry : mapRailwayLocal.entrySet())
		{
			Double[] temp = entry.getValue();
			if(Math.abs(new Date().getTime() - temp[2]) < 30000){ //������λ�ú͹�������λ����30s���ڣ���Ϊ��Ч
				double railwayLocal = findClosedLocal(temp[0], temp[1]);
				System.out.println("�𳵵�ǰλ��:"+railwayLocal);
				double dis = Math.abs(railwayLocal - peopleLocal);
				if(dis < result)
					result = dis;
				
			}
			
		}
		return result*1000;
	}
	
	
	// ���������뾭γ���������ĵ��KmMark
	private double findClosedLocal(double Latitude, double Longitude){

		// ������DatabaseGPS�������������ĵ�
		int i = 0;
		int j = DatabaseGPS.length - 1;
		while(i < j)
		{
			double disiToGive = Math.pow(Latitude-DatabaseGPS[i][0], 2) + Math.pow(Longitude-DatabaseGPS[i][1], 2);
			double disjToGive = Math.pow(Latitude-DatabaseGPS[j][0], 2) + Math.pow(Longitude-DatabaseGPS[j][1], 2);
			if(disiToGive < disjToGive)
				j = (i + j)/2; // ����ȡ��
			else
				i = (int) Math.ceil((i + j) / 2.0);

			
		}
		RailwayData temp = new RailwayData(new Double[]{DatabaseGPS[i][0],DatabaseGPS[i][1]});
		
		return map.get(temp);

	}
	
	// ��������
	public void insertLocal(int client_type,String client_number,double Latitude,double Longitude){
		try{	
			String sql = null;
			PreparedStatement preStmt = null;
			// ����Ϊ1�ǻ�
			if(client_type == 1){
				sql = "INSERT INTO xintanlurailwaylocal (railwayNum, Latitude, Longitude) VALUES (?,?,?)";
				
				Double[] insertData = new Double[3];
				insertData[0] = Latitude;
				insertData[1] = Longitude;
				insertData[2] = (double) new Date().getTime();
				// ����hashmap�����hashMap��ֵ
				mapRailwayLocal.put(client_number, insertData);
			}
			else { // ����Ϊ0�ǹ���
				sql = "INSERT INTO xintanluipqclocal (peopleNum, Latitude, Longitude) VALUES (?,?,?)";	
			}
			preStmt = con.prepareStatement(sql);	
			preStmt.setString(1, client_number);
			preStmt.setString(2, Double.toString(Latitude));
			preStmt.setString(3, Double.toString(Longitude));
			preStmt.executeUpdate();
			
			System.out.println("��������ʵʱλ���������");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// �ж��û��Ƿ����
	public boolean judgeLogin(int client_type,String client_number){
		boolean result = false;
		try{		
			Statement statement = con.createStatement();
			String sql = null;
			if(client_type == 1){ //����1λ��
				sql =  "SELECT count(*) as count1 from railwayinfo where railwayNum = " + Integer.valueOf(client_number);
			}
			else { // ����Ϊ0�ǹ���
				sql =  "SELECT count(*) as count1 from userinfo where userNum = " + Integer.valueOf(client_number);
			}
			ResultSet rs = statement.executeQuery(sql);
			if(rs.next()){
				int count = Integer.valueOf(rs.getString("count1"));
				if(count == 1)
					result = true;
			}			
			System.out.println("��½У�����");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
