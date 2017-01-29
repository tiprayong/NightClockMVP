package com.comtip.tip.nightclockmvp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    Timer timer;
    // font ตัวอักษร
    Typeface DigitalType;

    // SharedPreferneces และตัวแปรที่ต้องเก็บค่า
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    String remindMessage = "";
    boolean isAlarm = false;
    int hourAlarm = 0;
    int minuteAlarm = 0;
    int selectTech = 0;
    int selectColor = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        loadingShared();
        setupWidgets();
    }

    private void setupWidgets () {

        ActiveClock activeClock = new ActiveClock(this);
        activeClock.clock = (TextView) findViewById(R.id.clock);
        activeClock.dateTV = (TextView) findViewById(R.id.dateTV);
        activeClock.remindTV = (TextView) findViewById(R.id.remindTV);
        //font ที่ใช้งาน
        DigitalType = Typeface.createFromAsset(getAssets(), "fonts/neon_pixel-7.ttf");
        activeClock.clock.setTypeface(DigitalType);

        // เมนู
        MenuControl menuControl = new MenuControl(this,this);
        menuControl.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        menuControl.menuList = (ListView) findViewById(R.id.menuList);
        menuControl.setupDrawer();

        // เริ่มทำงานนาฬิกา
        timer = new Timer();
        timer.schedule(activeClock, 0, 1000);
    }

    public void loadingShared() {

        shared = this.getSharedPreferences("Save Mode", Context.MODE_PRIVATE);
        editor = shared.edit();
        remindMessage = shared.getString("remindMessage", "Created By TipRayong.");
        hourAlarm = shared.getInt("hourAlarm", 0);
        minuteAlarm = shared.getInt("minuteAlarm", 0);
        isAlarm = shared.getBoolean("isAlarm", false);
        selectTech = shared.getInt("selectTech", 0);
        selectColor = shared.getInt("selectColor", 0);

    }

    @Override
    protected void onPause() {
        super.onPause();

        editor.putString("remindMessage", remindMessage);
        editor.putInt("hourAlarm", hourAlarm);
        editor.putInt("minuteAlarm", minuteAlarm);
        editor.putBoolean("isAlarm", isAlarm);
        editor.putInt("selectTech", selectTech);
        editor.putInt("selectColor", selectColor);
        editor.commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
