package com.mystes.mytraineragain;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import static java.sql.DriverManager.println;

/**
 * 구현 : Android Studio 2.2.3 version
 * 실행 : Nexus_S_API_25
 * 로그인 : userID : test / Password : test
 * 관리자로그인 : owen1130@naver.com / dnjsduf23
 */

public class MainActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    Button join_btn, login_btn;
    EditText eUserID, ePassword;
    SQLiteDatabase db;
    String dbName, tableName;
    String loginID, loginPassword;
    CheckBox Auto_Login;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    boolean dbCreated = false;
    boolean tableCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbName = "againDB";
        createDatabase(dbName);
        tableName = "userTable";
        createTable(tableName);

        eUserID = (EditText) findViewById(R.id.userID);
        ePassword = (EditText) findViewById(R.id.password);
        Auto_Login = (CheckBox) findViewById(R.id.AutoLogin);

        /* 자동로그인을 위한 SharedPreference 설정 */
        setting = getSharedPreferences("Login", 0);   // key 값 = Login
        editor = setting.edit();

        /* 자동로그인이 다음 접속때도 계속 설정되도록 하는 기능 */
        if(setting.getBoolean("AutoLogin", false)){
            eUserID.setText(setting.getString("userID", ""));
            ePassword.setText(setting.getString("password", ""));
            Auto_Login.setChecked(true);
        }

        /* 회원가입 창으로 이동 */
        join_btn = (Button) findViewById(R.id.btn_join);
        join_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        /* 로그인 기능 */
        login_btn = (Button) findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginID = eUserID.getText().toString();
                loginPassword = ePassword.getText().toString();

                String password = helper.searchPass(loginID);    // 입력한 이메일에 대한 DB의 패스워드 저장
                if (loginID.equals("admin") && loginPassword.equals(password)) {
                    Toast.makeText(getApplication(), "로그인 완료",
                            Toast.LENGTH_SHORT).show();
                    Intent myintent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(myintent);
                }else {
                    if (loginPassword.equals(password)) {
                        Toast.makeText(getApplication(), "로그인 완료",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AgainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplication(), "아이디 또는 비밀번호 오류",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        /* 자동 로그인 설정 */
        Auto_Login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {     // 자동로그인이 체크되면
                    String userID = eUserID.getText().toString();
                    String password = ePassword.getText().toString();

                    /* preference에 메일과 비밀번호 저장, 자동로그인 체크 */
                    editor.putString("userID",userID);
                    editor.putString("password",password);
                    editor.putBoolean("AutoLogin",true);
                    editor.commit();
                }else {
                    /* 체크가 해제되면 기존 저장 정보 삭제 */
                    editor.remove("userID");
                    editor.remove("password");
                    editor.remove("AutoLogin");
                    editor.clear();
                    editor.commit();
                }
            }
        });

    }

    /* UserTable 생성 */
    private void createTable(String tableName) {
        println("Creating table : " + tableName);
        try {
            if (db != null) {
                db.execSQL("create table if not exists " + tableName + "("
                        + " id integer PRIMARY KEY autoincrement, "
                        + " userName text, "
                        + " userID text, "
                        + " password text, "
                        + " joinDate date);");

                println("Table is created.");

                tableCreate = true;
            } else {
                println("Database is not created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            println("Table is not created.");
        }
    }
    /* Again 디비 생성 */
    private void createDatabase(String dbName) {
        println("creating database : " + dbName);

        String databaseName = dbName;
        try {
            db = openOrCreateDatabase(databaseName, Activity.MODE_PRIVATE, null);

            dbCreated = true;

            println("Database is created.");
        } catch (Exception e) {
            e.printStackTrace();
            println("Database is not created.");
        }
    }
}