package com.example.railwayalarm_v10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WorkerLoginActivity extends AppCompatActivity {

    private Button login;
    private Button quit;
    private EditText client_num;

    public static WorkerLoginActivity _instance = null;
//    public static String inputNum;

//    private Intent bcIntent = new Intent("com.example.railwayalarm_v10.CLIENTRECEIVER");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);

        login = (Button)findViewById(R.id.login);
        quit = (Button)findViewById(R.id.quit);
        client_num = (EditText)findViewById(R.id.client_num);

        login.setOnClickListener(new ButtonListener());
        login.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if (v==login){
                String inputNum = client_num.getText().toString();
                Intent intent = new Intent(WorkerLoginActivity.this,WorkerActivity.class);
//                intent.putExtra("inputNum",inputNum);
                startActivity(intent);
                Intent intent1 = new Intent(WorkerLoginActivity.this,WorkerService.class);
                intent1.putExtra("inputNum",inputNum);
                startService(intent1);
//                finish();
            }
            if (v==quit){
                Intent intent = new Intent(WorkerLoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
