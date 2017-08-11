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

        System.out.println("���ܵ�����Ϣ:" + str);
        
        
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
        System.out.println("������Ϣ:" + arg1.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("IP:" + session.getRemoteAddress().toString() + "�Ͽ�����");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("IP:" + session.getRemoteAddress().toString() + "��������");
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
		// �����ݲ������ݿ�
		conDB.con.insertLocal(client_type, client_number, latitude, longitude);
		// ��ȡ�빤������Ļ𳵾���
		double distance = conDB.con.judgeDistance(latitude, longitude);
		if(distance < 20){
			System.out.println("������룺" + distance + " ����һ��Ԥ��");
		}else if(distance < 80){
			System.out.println("������룺" + distance + " ���ض���Ԥ��");
		}else if(distance < 100){
			System.out.println("������룺" + distance + " ��������Ԥ��");
		}else {
			System.out.println("������룺" + distance + "û��Σ��");
		}	
	}
}