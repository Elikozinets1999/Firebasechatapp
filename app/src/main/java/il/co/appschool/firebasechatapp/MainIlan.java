package il.co.appschool.firebasechatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import il.co.appschool.firebasechatapp.Ilan.Account;
import il.co.appschool.firebasechatapp.Ilan.AccountAdapter;
import il.co.appschool.firebasechatapp.Ilan.AccountOpenHelper;
import il.co.appschool.firebasechatapp.Ilan.InsertActivity;
import il.co.appschool.firebasechatapp.Ilan.InsertActivityacc;
import il.co.appschool.firebasechatapp.Ilan.Message;
import il.co.appschool.firebasechatapp.Ilan.MessageAdapter;
import il.co.appschool.firebasechatapp.Ilan.MessageOpenHelper;
import il.co.appschool.firebasechatapp.Ilan.UpdateActivity;
import il.co.appschool.firebasechatapp.Ilan.UpdateActivityacc;

public class MainIlan extends AppCompatActivity
{

    MessageOpenHelper coh;
    AccountOpenHelper aoh;

    ArrayList<Message> listOfMessage;
    ArrayList<Account> listOfAccount;

    ListView lv , lv2;

    BroadCastBattery brodcastbattery;

    TextView toastline;

    MessageAdapter messageAdapter;
    AccountAdapter accountAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ilan);

        brodcastbattery=new BroadCastBattery();

        listOfMessage=new ArrayList<Message>();
        listOfAccount=new ArrayList<Account>();

        coh=new MessageOpenHelper(this);
        aoh=new AccountOpenHelper(this);

        lv=(ListView)findViewById(R.id.lv);
        lv2=(ListView)findViewById(R.id.lv2);
        toastline=(TextView)findViewById(R.id.toastline);

        Log.d("Account", "list size is " + listOfAccount.size());
        Log.d("Account", AccountOpenHelper.CREATE_TABLE_ACCOUNT);

        Log.d("Message", "list size is " + listOfMessage.size());
        Log.d("Message", MessageOpenHelper.CREATE_TABLE_MESSAGE);

        coh.open();
        listOfMessage=coh.getAllmessages();
        coh.close();

        if(listOfMessage.size()==0)
        {
            createMessages();
        }
        createAccounts();
        messageAdapter=new MessageAdapter(this,0,listOfMessage);
        lv.setAdapter(messageAdapter);


        aoh.open();
        listOfAccount=aoh.getAllAccounts();
        aoh.close();

        if (listOfAccount.size()==0)
        {
            createAccounts();
        }
        accountAdapter=new AccountAdapter(this, 0,listOfAccount);
        lv2.setAdapter(accountAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO Auto-generated method stub
                Message c = (Message) messageAdapter.getItem(i); ///added (Message)
                Toast.makeText(getBaseContext(), c.getMessage() + "touched", Toast.LENGTH_SHORT).show();

                Intent g = new Intent((MainIlan.this), UpdateActivity.class);
                g.putExtra("rowId", c.getmessageId());
                startActivityForResult(g,0 );
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO Auto-generated method stub
                Account c = (Account) accountAdapter.getItem(i); ///added (Message)
                Toast.makeText(getBaseContext(), c.getFirstName() + "touched", Toast.LENGTH_SHORT).show();

                Intent g = new Intent((MainIlan.this), UpdateActivityacc.class);
                g.putExtra("rowId", c.getId());
                startActivityForResult(g,0 );
            }
        });





    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        registerReceiver(brodcastbattery,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        unregisterReceiver(brodcastbattery);
    }

    public void createMessages()
    {
        coh.open();
        Message c1=new Message(0,"12/10/2018","19:43","howareyoubro ?","shaked");
        c1=coh.createMessage(c1);
        listOfMessage.add(c1);

        Message c2=new Message(0,"22/11/2017","14:25","whereareyou ?","eli");
        c2=coh.createMessage(c2);
        listOfMessage.add(c2);
        coh.close();
        Log.d("Message", "list size is " + listOfMessage.size());
    }

    public void createAccounts()
    {
        aoh.open();
        Account c1=new Account(0,"ilan" , "moshayev", "amira2510", "ilan132465@walla.com", "Ujhsk25a4f");
        c1=aoh.createAccount(c1);
        listOfAccount.add(c1);

        Account c2=new Account(0, "eli", "kozinets" , "elilike2eat" , "elikozinets@gmail.com" , "Ujmdda25w9");
        c2=aoh.createAccount(c2);
        listOfAccount.add(c2);
        aoh.close();

        Log.d("Account" , "list size is :" + listOfAccount.size());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        coh.open();
        aoh.open();
        switch (item.getItemId()) {
            case R.id.menu_allMessages:
                listOfMessage=coh.getAllmessages();
                Log.i("filter", "list count is " + listOfMessage.size());
                refreshMyAdapter();
                break;
            case R.id.menu_acc:
                Intent d=new Intent(MainIlan.this, InsertActivityacc.class);
                startActivityForResult(d , 1);
                break;
            case R.id.menu_new:
                Intent i=new Intent(MainIlan.this,InsertActivity.class);
                startActivityForResult(i, 1);//Request code 1 is for ------>insert screen
            default:
                break;

        }
        coh.close();
        aoh.close();

        return super.onOptionsItemSelected(item);

    }
    public void refreshMyAdapter()
    {

        messageAdapter=new MessageAdapter(this,0,listOfMessage);
        lv.setAdapter(messageAdapter);

    }
    public void refreshMyAdapteracc()
    {

        accountAdapter=new AccountAdapter(this,0,listOfAccount);
        lv2.setAdapter(accountAdapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK)
        {
            coh.open();
            aoh.open();
            listOfMessage=coh.getAllmessages();
            listOfAccount=aoh.getAllAccounts();
            refreshMyAdapter();
            refreshMyAdapteracc();
            coh.close();
            aoh.close();

            if(requestCode==0)
            {
                Toast.makeText(getBaseContext(), "Database updated", Toast.LENGTH_SHORT).show();

            }
            else if (requestCode==1)
            {
                Toast.makeText(getBaseContext(), "New Customer add to database", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class BroadCastBattery extends BroadcastReceiver
    {

        @Override

        public void onReceive(Context context, Intent intent) {

            int battery = intent.getIntExtra("level",0);
            toastline.setText("Your phone has " + String.valueOf(battery) + "%");
//            if (battery == 20)
//            {
//                Toast.makeText(getBaseContext(),"You have only 20% left",Toast.LENGTH_LONG).show();
//            }

        }

    }



}