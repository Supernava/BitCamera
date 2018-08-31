package supernava.bitcombine.co.th.bitcamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import supernava.bitcombine.co.th.bitcamera.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //savedInstanceState เก็บความคืบหน้าของโปรแกรม เช่น ก่อนหน้านี้ทำอะไรอยู่ เพื่อให้กลับมาทำต่อได้
        //เช่น ก่อนหน้านี้ย่อ app ไว้ หากเปิดใหม่จะแสดงที่ย่อมาใช้งานต่อ
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Add Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFragmentMain, new MainFragment())
                    .commit();
        }
    }   // Main Method ทำงานหลัก มีได้ 1 ตัวเท่านั้น

}  // Main Class
