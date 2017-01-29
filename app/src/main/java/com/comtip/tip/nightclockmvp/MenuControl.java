package com.comtip.tip.nightclockmvp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by TipRayong on 28/1/2560 15:46
 * NightClockMVP
 */
public class MenuControl {
    // เมนู
    public ListView menuList = null;
    public DrawerLayout drawerLayout = null;
    final String[] menuItem = {"Remind", "Alarm", "Animation", "Text Color","Lullaby"};
    MainActivity main;
    Context context;

    public MenuControl(Context context, MainActivity main) {
        this.context = context;
        this.main = main;
    }

    public void setupDrawer() {
        if ((drawerLayout == null) && (menuList == null)) {
            return;
        } else {
            CustomList adapter = new CustomList(main, menuItem);
            menuList.setAdapter(adapter);
            menuList.setOnItemClickListener(new DrawerItemClickListener());
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawerLayout.closeDrawer(menuList);

            switch (position) {
                case 0:  //remind
                       remindSetup();
                    break;
                case 1:   // Alarm
                       alarmSetup();
                    break;
                case 2:   //  Animation
                       selectAnimation();
                    break;
                case 3:  // Text Color
                       selectTextColor();
                    break;

                case 4: // Lullaby

                    AlertDialog.Builder alertLoad = new AlertDialog.Builder(context);
                    alertLoad.setTitle("♨ Lullaby ♨");

                    alertLoad.setNegativeButton("✘ NO ✘", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //cancel
                        }
                    });
                    alertLoad.setPositiveButton("✔ YES ✔", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //         startLullaby();
                            Toast.makeText(context, "นอนฝันดี", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertL = alertLoad.create();
                    alertL.show();

                    break;
            }
        }
    }

    //  ตั้งข้อความ Remind
    public void remindSetup() {
        final Dialog remindDialog = new Dialog(context);
        remindDialog.setContentView(R.layout.remindmelayout);
        remindDialog.setTitle("Remind Me ,Please !!!!");
        remindDialog.setCanceledOnTouchOutside(false);
        final EditText remindEdit = (EditText) remindDialog.findViewById(R.id.remindEdit);
        final Button remindBT = (Button) remindDialog.findViewById(R.id.remindBT);
        final Button cancelRemindBT = (Button) remindDialog.findViewById(R.id.cancelRemindBT);

        remindBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.remindMessage = remindEdit.getText().toString();
                remindDialog.dismiss();
            }
        });

        cancelRemindBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remindDialog.dismiss();
            }
        });

        remindDialog.show();
    }

    //Alarm
    public void alarmSetup() {

        final Dialog alarmDialog = new Dialog(context);
        alarmDialog.setContentView(R.layout.alarmlayout);
        alarmDialog.setTitle("Alarm Setting");
        alarmDialog.setCanceledOnTouchOutside(false);


        String[] hourData = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"
                , "20", "21", "22", "23"};

        String[] minuteData = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"
                , "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"
                , "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"
                , "40", "41", "42", "43", "44", "45", "46", "47", "48", "49"
                , "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

        // สร้าง Hour Spinner
        final Spinner hourSP = (Spinner) alarmDialog.findViewById(R.id.hourSP);
        ArrayAdapter<String> adapterHour = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, hourData);
        adapterHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSP.setAdapter(adapterHour);
        hourSP.setSelection(main.hourAlarm);

        final Spinner minuteSP = (Spinner) alarmDialog.findViewById(R.id.minuteSP);
        ArrayAdapter<String> adapterMinute = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, minuteData);
        adapterMinute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSP.setAdapter(adapterMinute);
        minuteSP.setSelection(main.minuteAlarm);

        final CheckBox alarmCB = (CheckBox) alarmDialog.findViewById(R.id.alarmCB);
        if (main.isAlarm) {
            alarmCB.setChecked(true);
        }

        alarmCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    main.isAlarm = true;
                } else {
                    main.isAlarm = false;
                }
            }
        });

        final Button alarmBT = (Button) alarmDialog.findViewById(R.id.alarmBT);
        alarmBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (main.isAlarm) {
                    main.hourAlarm = hourSP.getSelectedItemPosition();
                    main.minuteAlarm = minuteSP.getSelectedItemPosition();
                }

                alarmDialog.dismiss();
            }
        });

        alarmDialog.show();

    }

    // selcet  Animation
    public void selectAnimation() {
        final Dialog techDialog = new Dialog(context);
        techDialog.setContentView(R.layout.selecttechlayout);
        techDialog.setTitle("Animation Setting");
        techDialog.setCanceledOnTouchOutside(false);

        String[] animationList = {
                "BounceUD",
                "BounceLR",
                "FadeIn",
                "Flip",
                "ZoomIn",
                "SlideInUp",
                "SlideInDown",
                "SlideInRight",
                "SlideInLeft",
                "Random"
        };

        // สร้าง Hour Spinner
        final Spinner techSP = (Spinner) techDialog.findViewById(R.id.techSP);
        ArrayAdapter<String> adapterTech = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, animationList);
        adapterTech.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        techSP.setAdapter(adapterTech);
        techSP.setSelection(main.selectTech);

        final Button techBT = (Button) techDialog.findViewById(R.id.techBT);
        techBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.selectTech = techSP.getSelectedItemPosition();
                techDialog.dismiss();
            }
        });

        techDialog.show();
    }

    // Select  Text Color
    public void selectTextColor() {
        final Dialog colorDialog = new Dialog(context);
        colorDialog.setContentView(R.layout.selecttechlayout);
        colorDialog.setTitle("Text Color Setting");
        colorDialog.setCanceledOnTouchOutside(false);

        String[] colorList = {
                "Green Ritsuko",
                "Blue Azusa",
                "Yellow Miki",
                "Red Hibiki",
                "White Takane",
        };

        // สร้าง Hour Spinner
        final Spinner techSP = (Spinner) colorDialog.findViewById(R.id.techSP);
        ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, colorList);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        techSP.setAdapter(adapterColor);
        techSP.setSelection(main.selectColor);

        final Button techBT = (Button) colorDialog.findViewById(R.id.techBT);
        techBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.selectColor = techSP.getSelectedItemPosition();
                colorDialog.dismiss();
            }
        });

        colorDialog.show();
    }


}
