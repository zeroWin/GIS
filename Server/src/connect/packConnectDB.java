package connect;

public class packConnectDB {
	public connectDB con = null;
	
	public packConnectDB(){
		con = new connectDB();
		con.loadSqlData();
	}
	
	
}
