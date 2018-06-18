package il.co.appschool.firebasechatapp.Ilan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import il.co.appschool.firebasechatapp.R;

public class UpdateActivityacc extends AppCompatActivity {

    EditText fname;
    EditText Lname;
    EditText displayname;
    EditText Email;
    EditText accspecId;
    Button btnDelete;
    Button btnSave;
    TextView tvId;
    AccountOpenHelper aoh;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_activityacc);

        aoh = new AccountOpenHelper(this);
        init();

    }

    public void init()/// updating specific Message( by click ) with new parameters
    {
        fname=(EditText) findViewById(R.id.updteaccfname);
        Lname=(EditText) findViewById(R.id.updateacclname);
        displayname=(EditText) findViewById(R.id.updateaccdisplayname);
        Email=(EditText) findViewById(R.id.updateaccemail);
        accspecId=(EditText) findViewById(R.id.updateaccspecid);
        btnDelete=(Button) findViewById(R.id.updateaccBtnDelete);
        btnSave=(Button) findViewById(R.id.updateaccBtnSave);
        tvId=(TextView) findViewById(R.id.updateaccId);

        id=getIntent().getLongExtra("rowId", 0);
        if(id!=0)
        {
            aoh.open();
            Account c=aoh.getAccountById(id);
            fname.setText(c.getFirstName());
            Lname.setText(c.getLastName());
            displayname.setText(c.getDisplayName());
            Email.setText(c.getEmail());
            tvId.setText("Details of messsage" + c.getId());
            aoh.close();


        }
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String ffname=fname.getText().toString();
                String llname=Lname.getText().toString();
                String ddisplayname=displayname.getText().toString();
                String eeemail=Email.getText().toString();
                String acccspecid=accspecId.getText().toString();


                Account c=new Account(id,ffname,llname,ddisplayname,eeemail,acccspecid);
                aoh.open();
                aoh.updateByRowacc(c);
                aoh.close();
                Intent i=new Intent();
                setResult(RESULT_OK,i);
                finish();


            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                aoh.open();
                aoh.deleteAccountByRow(id);
                aoh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();


            }
        });
    }

}
