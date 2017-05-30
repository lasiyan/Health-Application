package com.mystes.mytraineragain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lasiy on 2017-05-24.
 */

public class AdminActivity extends AppCompatActivity {
    DBHelper dbHelper;  // 회원 정보 관리를 위한 DB class
    EditText eUserID;  // 이름 입력 창
    TextView resultView;
    Button btnAll, btnSel, btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        dbHelper = new DBHelper(this);

        eUserID = (EditText)findViewById(R.id.admin_input);
        resultView = (TextView)findViewById(R.id.result_textview);

        btnAll = (Button)findViewById(R.id.result_all);
        btnSel = (Button)findViewById(R.id.result_sel);
        btnDel = (Button)findViewById(R.id.result_del);

        /* 모든 회원 보기 버튼 구현 */
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView.setText(dbHelper.printData());   // 결과창에 데이터 출력
            }
        });

        /* 특정 회원 검색 기능 구현*/
        btnSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = eUserID.getText().toString();

                if(ID.getBytes().length <= 0) {       // 회원 ID가 0 자리 이하이면 메세지 출력
                    Toast.makeText(getApplication(), "검색할 회원 ID를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    resultView.setText(dbHelper.select(ID));
                    if(dbHelper.select(ID).equals("")) {
                        Toast.makeText(getApplication(), "해당 회원 정보가 존재하지 않습니다",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /* 특정 회원 삭제 기능 구현*/
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = eUserID.getText().toString();

                if(ID.getBytes().length <= 0) {       // 회원 ID가 0 자리 이하이면 메세지 출력
                    Toast.makeText(getApplication(), "검색할 회원 ID를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                }else {
                    dbHelper.delete(ID);
                    resultView.setText(dbHelper.printData());
                }
            }
        });
    }
}
