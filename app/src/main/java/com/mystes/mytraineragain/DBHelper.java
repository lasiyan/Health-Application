package com.mystes.mytraineragain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lasiy on 2017-05-24.
 */

/* SQLite 사용을 위한 DBHelper 클래스 구현 */
public class DBHelper extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;
    Cursor cursor;

    //데이타베이스 이름
    private static final String DATABASE_NAME = "againDB";
    //데이터베이스 버전
    private static final int DATABASE_VERSION = 1;
    //테이블 이름
    private static final String TABLE_NAME = "userTable";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /* 로그인 기능을 위한 비밀번호 매칭 메서드 */
    public String searchPass(String userID) {
        db = this.getReadableDatabase();    // DB 정보를 읽기 전용으로 가져온다
        String query = "select userID, password from " + TABLE_NAME + ";";   // SQL문에서 userID와 password 선택
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";    // return 할 b의 초기값 저장

        if (cursor.moveToFirst()) { // 커서를 select로 선택한 정보의 첫 위치로 이동
            do {
                a = cursor.getString(0);  // 첫 열의 정보 저장

                if (a.equals(userID)) {  // 입력한 userID
                    b = cursor.getString(1);    // userID와 매칭된 패스워드 비교
                    break;              // 패스워드가 맞으면 종료
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        return b;

    }
    public void delete(String ID) {
        db = getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE userID = '"+ID+"';");
        db.close();
    }
    public String select(String ID) {
        db = getReadableDatabase();
        String str = "";

        cursor = db.rawQuery("SELECT id, userName, userID FROM "+TABLE_NAME+" WHERE userID LIKE '"+ ID + "%'", null);

        while (cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " . 이름: "
                    + cursor.getString(1)
                    + ", 아이디: "
                    + cursor.getString(2)
                    + "\n";
        }
        cursor.close();
        return str;
    }
    public String printData() {
        db = getReadableDatabase();
        String str = "";

        cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        while (cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " . 이름: "
                    + cursor.getString(1)
                    + ", 아이디: "
                    + cursor.getString(2)
                    + "\n";
        }
        cursor.close();
        return str;
    }

}
