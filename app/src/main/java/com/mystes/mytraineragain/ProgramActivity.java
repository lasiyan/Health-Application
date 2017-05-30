package com.mystes.mytraineragain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lasiy on 2017-05-24.
 */

public class ProgramActivity extends Activity implements View.OnClickListener {
    static ArrayList<String> programList;

    private Button[] program = new Button[6];
    String[][] exercise = new String[6][9];
    int[][] exerLayout = new int[6][3];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);

        program[0] = (Button) findViewById(R.id.program_1);
        program[1] = (Button) findViewById(R.id.program_2);
        program[2] = (Button) findViewById(R.id.program_3);
        program[3] = (Button) findViewById(R.id.program_4);
        program[4] = (Button) findViewById(R.id.program_5);
        program[5] = (Button) findViewById(R.id.program_6);

        programList = new ArrayList<String>();
        programList.add(0, "맞춤형 다이어트");
        programList.add(1, "하루 15분 다이어트");
        programList.add(2, "단기 다이어트");
        programList.add(3, "신랑을 위한 웨딩 다이어트");
        programList.add(4, "신부를 위한 웨딩 다이어트");
        programList.add(5, "비키니 다이어트");

        for (int i = 0; i < 6; i++) {
            program[i].setText(programList.get(i));
            program[i].setTag(i);
            program[i].setOnClickListener(this);
        }

        exercise[0][0] = getString(R.string.buffy); exercise[0][1] = getString(R.string.buffyInfo);
        exercise[0][2] = getString(R.string.lungy); exercise[0][3] = getString(R.string.lungyInfo);
        exercise[0][4] = getString(R.string.pullup); exercise[0][5] = getString(R.string.pullupInfo);
        exerLayout[0][0] = R.layout.buffytest; exerLayout[0][1] = R.layout.lungy; exerLayout[0][2] = R.layout.pullup;

        exercise[1][0] = getString(R.string.buffy); exercise[1][1] = getString(R.string.buffyInfo);
        exercise[1][2] = getString(R.string.running); exercise[1][3] = getString(R.string.runningInfo);
        exercise[1][4] = getString(R.string.pushup); exercise[1][5] = getString(R.string.pushupInfo);
        exerLayout[1][0] = R.layout.buffytest; exerLayout[1][1] = R.layout.running; exerLayout[1][2] = R.layout.pushup;

        exercise[2][0] = getString(R.string.running); exercise[2][1] = getString(R.string.runningInfo);
        exercise[2][2] = getString(R.string.bench); exercise[2][3] = getString(R.string.benchInfo);
        exercise[2][4] = getString(R.string.lungy); exercise[2][5] = getString(R.string.lungyInfo);
        exerLayout[2][0] = R.layout.running; exerLayout[2][1] = R.layout.bench; exerLayout[2][2] = R.layout.lungy;

        exercise[3][0] = getString(R.string.bench); exercise[3][1] = getString(R.string.benchInfo);
        exercise[3][2] = getString(R.string.pullup); exercise[3][3] = getString(R.string.pullupInfo);
        exercise[3][4] = getString(R.string.lungy); exercise[3][5] = getString(R.string.lungyInfo);
        exerLayout[3][0] = R.layout.bench; exerLayout[3][1] = R.layout.pullup; exerLayout[3][2] = R.layout.lungy;

        exercise[4][0] = getString(R.string.lungy); exercise[4][1] = getString(R.string.lungyInfo);
        exercise[4][2] = getString(R.string.pushup); exercise[4][3] = getString(R.string.pushupInfo);
        exercise[4][4] = getString(R.string.pullup); exercise[4][5] = getString(R.string.pullupInfo);
        exerLayout[4][0] = R.layout.lungy; exerLayout[4][1] = R.layout.pushup; exerLayout[4][2] = R.layout.pullup;

        exercise[5][0] = getString(R.string.buffy); exercise[5][1] = getString(R.string.buffyInfo);
        exercise[5][2] = getString(R.string.pushup); exercise[5][3] = getString(R.string.pushupInfo);
        exercise[5][4] = getString(R.string.lungy); exercise[5][5] = getString(R.string.lungyInfo);
        exerLayout[5][0] = R.layout.buffytest; exerLayout[5][1] = R.layout.pushup; exerLayout[5][2] = R.layout.lungy;
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        for (Button tmpBtn : program) {
            if (tmpBtn == btn) {
                final int s = (Integer) v.getTag();

                try {
                    Context mContext = getApplicationContext();
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    //R.layout.program[번호]는 xml 파일명이고  R.id.popup[번호]는 보여줄 레이아웃 아이디
                    View layout = inflater.inflate(R.layout.program+s+1, (ViewGroup) findViewById(R.id.popup));
                    AlertDialog.Builder aDialog = new AlertDialog.Builder(ProgramActivity.this);

                    aDialog.setTitle(programList.get(s));
                    aDialog.setView(layout); //program1.xml 파일을 뷰로 셋팅
                    //시작버튼을 위한 부분
                    aDialog.setNegativeButton("시작", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ProgramActivity.this, AgainActivity.class);
                            intent.putExtra("msg", "exer" + (s + 1));
                            intent.putExtra("exerList", exercise[s]);
                            intent.putExtra("layoutList", exerLayout[s]);
                            startActivity(intent);
                        }
                    });
                    //팝업창 생성
                    AlertDialog ad = aDialog.create();
                    ad.show();//설정한 레이아웃 출력
                } catch (Exception e) {
                    Toast.makeText(getApplication(), "프로그램 없음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}