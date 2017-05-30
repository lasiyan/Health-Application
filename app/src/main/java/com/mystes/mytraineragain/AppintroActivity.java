package com.mystes.mytraineragain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lasiy on 2017-05-24.
 */

public class AppintroActivity extends AppCompatActivity {
    TextView phone, site;
    String tel, url1, url2;
    Uri number, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_introduce);

        url1 = "http://www.dietshin.com/";      // 관련 사이트 1
        url2 = "http://cafe.naver.com/cantsb";  // 관련 사이트 2
    }

    public void onTelClicked(View v) {
        phone = (TextView)findViewById(R.id.devel_phone);
        tel = (String)phone.getText();      // textView를 string 형식으로 가져옴

        number = Uri.parse("tel:" + tel);
        Intent intent = new Intent(Intent.ACTION_DIAL,number); // intent에 전화번호 정보를 담아서
        startActivity(intent);  // 클릭 시 이벤트 발생
    }

    public void onSite1Clicked(View v) {
        site = (TextView)findViewById(R.id.site_1);

        url = Uri.parse(url1);  // 관련사이트 1의 주소를 parse 시키고
        Intent intent = new Intent(Intent.ACTION_VIEW,url); // intent에 url 정보를 담아서
        startActivity(intent);  // 클릭 시 이벤트 발생
    }

    public void onSite2Clicked(View v) {
        site = (TextView)findViewById(R.id.site_2);

        url = Uri.parse(url2);
        Intent intent = new Intent(Intent.ACTION_VIEW,url);
        startActivity(intent);
    }
}
