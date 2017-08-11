package client;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;


import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import common.BaseConfig;

/**
 * Created by Loftor on 2014/8/15.
 */
public class MinaTimeClient {

    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
        connector.setHandler(new TimeClientHander());
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(BaseConfig.address,BaseConfig.PORT));
        //等待建立连接
        connectFuture.awaitUninterruptibly();
        System.out.println("连接成功");

        IoSession session = connectFuture.getSession();

//        Scanner sc = new Scanner(System.in);
//
//        boolean quit = false;
//
//        while(!quit){
//
//            String str = sc.next();
//            if(str.equalsIgnoreCase("quit")){
//                quit = true;
//            }
//            session.write(str);
//        }
        long temp = new Date().getTime();
        int client_num = 3;
        int packet_num = 1;
        int clentType = 0;
        boolean quit = false;
        while(!quit)
        {
        	long nowtime = new Date().getTime();
        	if(nowtime - temp > 2000){ //两秒发一下
        		String str = client_num+","+packet_num+","+clentType;
        		session.write(str);
        		temp = nowtime;
        		++packet_num;
            	if(str.equalsIgnoreCase("quit")){
                    quit = true;
                }
        	}

        }
        
        
        //关闭
        if(session!=null){
            if(session.isConnected()){
                session.getCloseFuture().awaitUninterruptibly();
            }
            connector.dispose(true);
        }


    }

}