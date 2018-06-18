package il.co.appschool.firebasechatapp.Ilan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import il.co.appschool.firebasechatapp.R;

public class InsertActivityacc extends AppCompatActivity {

    EditText fname;
    EditText Lname;
    EditText displayname;
    EditText Email;
    EditText accspecId;
    Button btninsert;
    TextView tvId;
    AccountOpenHelper aoh;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_activityacc);
        aoh = new AccountOpenHelper(this);
        init();

    }

    public void init() /// we inserting new account to his database
    {
        fname=(EditText) findViewById(R.id.insertaccfname);
        Lname=(EditText) findViewById(R.id.insertacclname);
        displayname=(EditText) findViewById(R.id.insertaccdisplayname);
        Email=(EditText) findViewById(R.id.insertaccemail);
        btninsert=(Button) findViewById(R.id.insertaccBtnSave);
        accspecId=(EditText) findViewById(R.id.insertaccspecid);
        tvId=(TextView) findViewById(R.id.inserttvaccId);
        btninsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String firstname = fname.getText().toString();
                String lastname = Lname.getText().toString();
                String displaynam = displayname.getText().toString();
                String emaill = Email.getText().toString();
                String accSpecIdd = accspecId.getText().toString();
                Account c = new Account(firstname, lastname, displaynam, emaill, accSpecIdd); /// deleated the id-0
                aoh.open();
                c = aoh.createAccount(c);
                aoh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();


            }
        });

    }

}