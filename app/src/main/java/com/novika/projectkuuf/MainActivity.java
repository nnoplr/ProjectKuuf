package com.novika.projectkuuf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_REPLY = 1;
    final static public String DATAS_CODE = "DATAS";
    private final String KEY = "KEY";
    EditText loginUsername, loginPassword;
    Button loginBtn, regisBtn;

    KuufDB kuufDB;
    ArrayList<DataUser> userList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kuufDB = new KuufDB(this);

        loginUsername = findViewById(R.id.LoginIsiUsername);
        loginPassword = findViewById(R.id.LoginIsiPassword);
        loginBtn = findViewById(R.id.BtnLogin);
        regisBtn = findViewById(R.id.BtnRegister);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<DataUser> dataUserArrayList = kuufDB.getAllUsers();

                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                if (username.equals("")) {
                    Toast.makeText(getApplicationContext(), "Username field must be filled", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Password field must be filled", Toast.LENGTH_SHORT).show();
                }
                // VALIDASI ADA DI DATABASE ATAU TDK

                else {
                    int size = dataUserArrayList.size();
                    boolean cek = false;
                    int i=0;
                    for (i=0; i < size; i++) {
                        if (username.equals(dataUserArrayList.get(i).getUsername()) && password.equals(dataUserArrayList.get(i).getPassword())) {
                            cek = true;
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                            SharedPreferences.Editor spp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                            spp.putInt("SELECTEDID", dataUserArrayList.get(i).getID());

                            spp.apply();

                            finish();
                            startActivity(intent);

                        }
                    }

                    if(cek==false){
                        Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                finish();
                startActivity(intent);
            }
        });

    }

}
