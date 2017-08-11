package com.example.railwayalarm_v10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RailwayLoginActivity extends AppCompatActivity {

    private Button login;
    private Button quit;
    private EditText client_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_login);
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
                Intent intent = new Intent(RailwayLoginActivity.this,RailwayActivity.class);
                intent.putExtra("inputNum",inputNum);
                startActivity(intent);
                finish();
            }
            if (v==quit){
                Intent intent = new Intent(RailwayLoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
