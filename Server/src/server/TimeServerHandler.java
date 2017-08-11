package server;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import KDtree.KeySizeException;
import common.BaseConfig;
import connect.connectDB;
import connect.packConnectDB;

/**
 * Created by Loftor on 2014/8/15.
 */
public class TimeServerHandler implements IoHandler {

	packConnectDB conDB = new packConnectDB();
    @Override
    public void exceptionCaught(IoSession arg0, Throwable arg1)
            throws Exception {
        arg1.printStackTrace();

    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        String str = message.toString();

        System.out.println("接受到的消息:" + str);
        
        
        String[] coor = str.split(",");
        getAlarmInfo(Integer.valueOf(coor[2]),"1",Double.valueOf(coor[0]),Double.valueOf(coor[1]));
        if (str.trim().equalsIgnoreCase("quit")) {
            session.close(true);
            return;
        }
        Date date = new Date();
        session.write(date.toString());
        System.out.println("Message written...");
    }

    @Override
    public void messageSent(IoSession arg0, Object arg1) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("发送信息:" + arg1.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("IP:" + session.getRemoteAddress().toString() + "断开连接");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("IP:" + session.getRemoteAddress().toString() + "建立连接");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("IDLE " + session.getIdleCount(status));

    }

    @Override
    public void sessionOpened(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub
    }

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void getAlarmInfo(int client_type,String client_number,double longitude,double latitude) throws KeySizeException{
		// 将数据插入数据库
		conDB.con.insertLocal(client_type, client_number, latitude, longitude);
		// 获取离工人最近的火车距离
		double distance = conDB.con.judgeDistance(latitude, longitude);
		if(distance < 20){
			System.out.println("最近距离：" + distance + " 返回一级预警");
		}else if(distance < 80){
			System.out.println("最近距离：" + distance + " 返回二级预警");
		}else if(distance < 100){
			System.out.println("最近距离：" + distance + " 返回三级预警");
		}else {
			System.out.println("最近距离：" + distance + "没有危险");
		}	
	}
}