package il.co.appschool.firebasechatapp.Ilan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import il.co.appschool.firebasechatapp.R;

public class UpdateActivity extends AppCompatActivity {

    EditText etmsgsaveday;
    EditText etmsgsavehour;
    EditText etmessage;
    EditText etfromwho;
    Button btnsave;
    Button btndelete;
    TextView tvId;
    MessageOpenHelper coh;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        coh=new MessageOpenHelper(this);
        init();

    }


    public void init()
    {
        etmsgsaveday=(EditText) findViewById(R.id.updateEtmsgday);
        etmsgsavehour=(EditText) findViewById(R.id.updateEtmsghour);
        etmessage=(EditText) findViewById(R.id.updateEtmsg);
        etfromwho=(EditText) findViewById(R.id.updateEtfromwho);

        btnsave=(Button) findViewById(R.id.updateBtnSave);
        btndelete=(Button) findViewById(R.id.updateBtnDelete);
        tvId=(TextView) findViewById(R.id.updateId);

        id=getIntent().getLongExtra("rowId", 0);
        if(id!=0)
        {
            coh.open();
            Message c=coh.getCustomerById(id);
            etmsgsaveday.setText(c.getMsgSaveDay());
            etmsgsavehour.setText(c.getMsgSaveHour());
            etmessage.setText(c.getMessage());
            etfromwho.setText(c.getFromWhichChat());
            tvId.setText("Details of messsage" + c.getmessageId());
            coh.close();


        }
        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String daymsg=etmsgsaveday.getText().toString();
                String hourmsg=etmsgsavehour.getText().toString();
                String mmsg=etmessage.getText().toString();
                String from=etfromwho.getText().toString();


                Message c=new Message(id,daymsg,hourmsg,mmsg,from);
                coh.open();
                coh.updateByRowmsg(c);
                coh.close();
                Intent i=new Intent();
                setResult(RESULT_OK,i);
                finish();


            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                coh.open();
                coh.deletemessagebyRow(id);
                coh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();


            }
        });


    }


}
