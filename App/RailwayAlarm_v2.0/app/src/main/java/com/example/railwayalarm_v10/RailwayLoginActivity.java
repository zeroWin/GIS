package com.example.railwayalarm_v10;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class RailwayLoginActivity extends AppCompatActivity {

    private Button login;
    private Button quit;
    private EditText client_num;

    private String inputNum;

    private String Addr;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_login);
        login = (Button)findViewById(R.id.login);
        quit = (Button)findViewById(R.id.quit_Railway);
        client_num = (EditText)findViewById(R.id.client_num);

        Addr = Variable.socketAddr;
        port = Variable.socketPort;

        login.setOnClickListener(new ButtonListener());
        quit.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if (v==login){
                inputNum = client_num.getText().toString();
                MyThread myThread = new MyThread();
                myThread.start();
//                Intent intent = new Intent(RailwayLoginActivity.this,RailwayActivity.class);
//                intent.putExtra("inputNum",inputNum);
//                startActivity(intent);
//                finish();
            }
            if (v==quit){
//                Toast.makeText(RailwayLoginActivity.this,"quit",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RailwayLoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            int mode;

            mode = msg.what;
            if (mode==11){
                Toast.makeText(RailwayLoginActivity.this,inputNum+"登录成功",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RailwayLoginActivity.this,RailwayActivity.class);
                intent.putExtra("inputNum",inputNum);
                startActivity(intent);
            }
            else {
                Intent intent_this = new Intent(RailwayLoginActivity.this,RailwayLoginActivity.class);
                startActivity(intent_this);
                Toast.makeText(RailwayLoginActivity.this,"用户名不存在！！！",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    };

    public class MyThread extends Thread{
        @Override
        public void run(){
            try {
                int i = 0;

                i = acceptServer();
                Message msg = new Message();
                msg.what = i;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int acceptServer() throws IOException{
//        Socket socket = new Socket("192.168.90.17",9090);
//        Socket socket = new Socket("1622l81m38.51mypc.cn",26647);//此处为服务器地址+端口
//        String Addr1 = "1622l81m38.51mypc.cn";
        Socket socket = new Socket(Addr,port);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);


        dos.writeInt(1);//0：工人------1：火车
        dos.writeUTF(inputNum);

        int num = 0;
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        num = dis.readInt();
        socket.shutdownOutput();
        socket.close();

        dos.close();
        dis.close();
        return num;
    }
}
