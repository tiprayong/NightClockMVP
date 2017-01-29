package com.comtip.tip.nightclockmvp;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by TipRayong on 27/1/2560 15:09
 * NightClockMVP
 */
public class ActiveClock extends TimerTask {
    public TextView clock = null;
    public TextView dateTV = null;
    public TextView remindTV = null;

    Date date;
    SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("th", "TH"));
    Calendar calendar = GregorianCalendar.getInstance();
    int hour, minute, second;
    String hourText, minuteText;
    String hourAlarmText, minuteAlarmText;
    MainActivity main;

    // random
    Random randTech = new Random();

    public ActiveClock(MainActivity main) {
        this.main = main;
    }

    @Override
    public void run() {

        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //รับค่าเวลาจากระบบ และทำการจัดรูปแบบตัวเลขใหม่
                date = Calendar.getInstance().getTime();
                calendar.setTime(date);
                hour = calendar.get(Calendar.HOUR_OF_DAY);  // 0 - 23 ระบบ 24 ชม. เสมอ
                minute = calendar.get(Calendar.MINUTE);
                second = calendar.get(Calendar.SECOND);

                //แปลง ชั่วโมง นาที วินาที จาก int ให้อยู่ในรูปแบบ String เพื่อใช้ในการแสดงผล
                if (hour < 10) {
                    hourText = "0" + hour;
                } else {
                    hourText = "" + hour;
                }

                if (minute < 10) {
                    minuteText = "0" + minute;
                } else {
                    minuteText = "" + minute;
                }
                adjustTextView();

            }
        });

    }


    private void adjustTextView() {
        if ((clock == null) && (dateTV == null) && (remindTV == null)) {
            return;
        } else {

            //กำหนดสีตัวอักษร
            textColor(main.selectColor);

            // แสดงนาฬิกา
            clock.setText(hourText + " " + minuteText);

            // แสดง Remind message
            remindTV.setText(main.remindMessage);

            //  แสดงวันที่ และกรณีมีการตั้ง Alarm
            if (main.isAlarm) {   //ส่วนแสดงวันที่  กรณีมีการตั้ง Alarm
                if (main.hourAlarm < 10) {
                    hourAlarmText = "0" + main.hourAlarm;
                } else {
                    hourAlarmText = "" + main.hourAlarm;
                }

                if (main.minuteAlarm < 10) {
                    minuteAlarmText = "0" + main.minuteAlarm;
                } else {
                    minuteAlarmText = "" + main.minuteAlarm;
                }
                hourAlarmText = "҉" + hourAlarmText + ":" + minuteAlarmText + "҉ ";
                dateTV.setText(hourAlarmText + formatter.format(date));
                //แจ้งเตือนเมื่อถึงเวลาที่กำหนดไว้
                if ((hour == main.hourAlarm) && (minute == main.minuteAlarm) && (second < 9)) {
                    alarmActive();
                }

            } else {    //  //ส่วนแสดงวันที่  กรณีไม่ได้ใช้งาน Alarm
                dateTV.setText(formatter.format(date));
            }

            //Animation   5 วินาที ทำ 1 ครั้ง
            if (second % 5 == 0) {
                if (main.selectTech < 9) {
                    clockSetTechniques(main.selectTech);
                } else {
                    clockSetTechniques(randTech.nextInt(9));
                }
            }
        }

    }

    // set TextColor
    public void textColor(int select) {
        switch (select) {
            case 0:
                clock.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.greenritsuko));
                dateTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.greenritsuko));
                remindTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.greenritsuko));
                break;

            case 1:
                clock.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.blueazusa));
                dateTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.blueazusa));
                remindTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.blueazusa));
                break;

            case 2:
                clock.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.yellowmiki));
                dateTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.yellowmiki));
                remindTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.yellowmiki));
                break;

            case 3:
                clock.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.redhibiki));
                dateTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.redhibiki));
                remindTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.redhibiki));
                break;

            case 4:
                clock.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.whitetakane));
                dateTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.whitetakane));
                remindTV.setTextColor(ContextCompat.getColor(main.getBaseContext(), R.color.whitetakane));
                break;
        }
    }


    // set Animation
    public void clockSetTechniques(int select) {
        switch (select) {
            case 0:
                YoYo.with(Techniques.BounceIn).duration(1000).playOn(clock);
                YoYo.with(Techniques.BounceInDown).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.BounceInUp).duration(1000).playOn(remindTV);
                break;

            case 1:
                YoYo.with(Techniques.BounceIn).duration(1000).playOn(clock);
                YoYo.with(Techniques.BounceInLeft).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.BounceInRight).duration(1000).playOn(remindTV);
                break;

            case 2:
                YoYo.with(Techniques.FadeIn).duration(1000).playOn(clock);
                YoYo.with(Techniques.FadeInDown).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.FadeInUp).duration(1000).playOn(remindTV);
                break;

            case 3:
                YoYo.with(Techniques.FlipInX).duration(1000).playOn(clock);
                YoYo.with(Techniques.FlipInX).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.FlipInX).duration(1000).playOn(remindTV);
                break;

            case 4:
                YoYo.with(Techniques.ZoomIn).duration(1000).playOn(clock);
                YoYo.with(Techniques.ZoomIn).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.ZoomIn).duration(1000).playOn(remindTV);
                break;

            case 5:
                YoYo.with(Techniques.SlideInUp).duration(1000).playOn(clock);
                YoYo.with(Techniques.SlideInUp).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.SlideInUp).duration(1000).playOn(remindTV);
                break;


            case 6:

                YoYo.with(Techniques.SlideInDown).duration(1000).playOn(clock);
                YoYo.with(Techniques.SlideInDown).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.SlideInDown).duration(1000).playOn(remindTV);
                break;

            case 7:
                YoYo.with(Techniques.SlideInRight).duration(1000).playOn(clock);
                YoYo.with(Techniques.SlideInRight).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.SlideInRight).duration(1000).playOn(remindTV);
                break;

            case 8:
                YoYo.with(Techniques.SlideInLeft).duration(1000).playOn(clock);
                YoYo.with(Techniques.SlideInLeft).duration(1000).playOn(dateTV);
                YoYo.with(Techniques.SlideInLeft).duration(1000).playOn(remindTV);
                break;

        }

    }

    // แจ้งเตือน Alarm
    public void alarmActive() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(main, uri);
        ringtone.play();
    }
}
