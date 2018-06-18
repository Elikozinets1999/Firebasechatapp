package il.co.appschool.firebasechatapp.Ilan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import il.co.appschool.firebasechatapp.R;

public class InsertActivity extends AppCompatActivity {
    EditText msgsaveday;            ///fname
    EditText msgsavehour;           ///lname
    EditText message;               ///message
    EditText fromwho;               ///waste
    TextView tvId;
    Button btnsave;
    MessageOpenHelper coh;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        coh=new MessageOpenHelper(this);
        init();

    }

    public void init()
    {
        msgsaveday=(EditText) findViewById(R.id.insertmsgsaveday);
        msgsavehour=(EditText) findViewById(R.id.insertmsgsavehour);
        message=(EditText) findViewById(R.id.insertmessage);
        fromwho=(EditText) findViewById(R.id.insertfromwho);
        btnsave=(Button) findViewById(R.id.insertBtnSave);
        tvId=(TextView) findViewById(R.id.inserttvId);
        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String day = msgsaveday.getText().toString();
                String hour = msgsavehour.getText().toString();
                String msg = message.getText().toString();
                String who = fromwho.getText().toString();
                Message c = new Message(day, hour, msg, who); /// deleated the id-0
                coh.open();
                c = coh.createMessage(c);
                coh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();


            }
        });

    }
}
