package com.mystes.mytraineragain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.sql.DriverManager.println;

/**
 * Created by lasiy on 2017-05-24.
 */

public class JoinActivity extends MainActivity {

    EditText _Username, _userID, _Password;
    Button btn_join;
    String Username, userID, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        _Username = (EditText) findViewById(R.id.join_username);
        _userID = (EditText) findViewById(R.id.join_id);
        _Password = (EditText) findViewById(R.id.join_password);


        btn_join = (Button)findViewById(R.id.join_btn_join);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = insertRecord(tableName);

                println(count + "Records inserted! ");
            }
        });
    }

    private int insertRecord(String tableName) {
        int count = 1;

        Username = _Username.getText().toString();
        userID = _userID.getText().toString();
        Password = _Password.getText().toString();

        try {
            if (tableName != null) {
                if(Username.equals("")) {
                    Toast.makeText(getApplication(), "회원정보를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return 0;
                }
                else if(Username.getBytes().length > 0 && Username.getBytes().length < 8) {
                    Toast.makeText(getApplication(), "8자리 이상의 ID를 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return 0;
                }
                else {
                    db.execSQL("insert into "+tableName+"(username, userID, password) values ('"+
                            Username+"', '"+userID+"', '"+Password+"');");
                    count++;
                    println("Inserting records into table : "+ tableName);
                    Toast.makeText(getApplication(), "회원가입 완료",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            println("Record is not inserted.");
            Toast.makeText(getApplication(), "회원가입 실패",
                    Toast.LENGTH_SHORT).show();
        }
        db.close();

        return count;
    }
}