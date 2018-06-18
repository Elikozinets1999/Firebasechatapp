package il.co.appschool.firebasechatapp.Shaked;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import il.co.appschool.firebasechatapp.R;

public class BroadcastList extends AppCompatActivity {

    ArrayList<Broadcast> broadcasts;
    BroadcastHelper broadcastHelper;
    ArrayList<String> products;
    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_list);

        products = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, products);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(arrayAdapter);
        broadcastHelper = new BroadcastHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("broadcasts_info2", MODE_PRIVATE);
        String values = prefs.getString("key1", null);
        if (values != null) {
            Log.d("data2", values.toString());
            List<String> brList = Arrays.asList(values.split("Broadcast"));
            Log.d("data2", brList.toString());
            for (int i = 1; i < brList.size(); i++)
                products.add(brList.get(i).toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_option_brlistview, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clearList) {
            broadcastHelper.open();
            broadcastHelper.deleteAll();
            lv.setAdapter(null);
            SharedPreferences prefs = getSharedPreferences("broadcasts_info2", MODE_PRIVATE);
            prefs.edit().clear().commit();
            broadcastHelper.close();
        }
        return true;
    }
}
