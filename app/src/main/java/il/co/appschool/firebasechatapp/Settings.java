package il.co.appschool.firebasechatapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.internal.framed.Variant;

import java.lang.reflect.Field;

public class Settings extends AppCompatActivity {

    private RadioGroup radioGroupBackgrounds;
    private RadioButton rbtnColorWhite, rbtnColorYellow, rbtnColorBlue;
    private Button btnSetBackground;
    public SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroupBackgrounds = (RadioGroup) findViewById(R.id.radioGroupBackgrounds);
        rbtnColorWhite = (RadioButton) findViewById(R.id.rbtnColorWhite);
        rbtnColorYellow = (RadioButton) findViewById(R.id.rbtnColorYellow);
        rbtnColorBlue = (RadioButton) findViewById(R.id.rbtnColorBlue);



        sp=getApplicationContext().getSharedPreferences("settings",0);
        String background = sp.getString("background", null);
        if (background != null) {
            getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.parseColor(background));
//            if(background == "#ffffff")
//                radioGroupBackgrounds.check(R.id.rbtnColorWhite);
//            else if (background == "#ffff00")
//                radioGroupBackgrounds.check(R.id.rbtnColorYellow);
//            else if(background == "#0055FF")
//                radioGroupBackgrounds.check(R.id.rbtnColorBlue);

        }


        btnSetBackground = (Button) findViewById(R.id.btnSetBackground);
        btnSetBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupBackgrounds.getCheckedRadioButtonId();
                // find which radioButton is checked by id
                SharedPreferences.Editor editor=sp.edit();
                if (selectedId == rbtnColorWhite.getId()) {
                    getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.parseColor("#ffffff"));
                    String background = "#ffffff";//whatever color you want to put here put in hex
                    editor.putString("background", background);
                    editor.commit();
                } else if (selectedId == rbtnColorYellow.getId()) {
                    getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.parseColor("#ffff00"));
                    String background = "#ffff00";//whatever color you want to put here put in hex
                    editor.putString("background", background);
                    editor.commit();
                } else {
                    getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.parseColor("#0055FF"));
                    String background = "#0055FF";//whatever color you want to put here put in hex
                    editor.putString("background", background);
                    editor.commit();
                }
            }


            });

        }

    @Override
    protected void onResume() {
        super.onResume();
        String background = sp.getString("background", null);
        if(background == "#ffffff")
            radioGroupBackgrounds.check(R.id.rbtnColorWhite);
        else if (background == "#ffff00")
            radioGroupBackgrounds.check(R.id.rbtnColorYellow);
        else if(background == "#0055FF")
            radioGroupBackgrounds.check(R.id.rbtnColorBlue);

    }
}
