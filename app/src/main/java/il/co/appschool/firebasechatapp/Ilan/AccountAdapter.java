package il.co.appschool.firebasechatapp.Ilan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import il.co.appschool.firebasechatapp.R;

/**
 * Created by ailan on 03/04/2018.
 */

public class AccountAdapter extends ArrayAdapter {
    Context context;
    List<Account> objects;
    public AccountAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_row2, parent, false);

        TextView tvaccFname = (TextView) view.findViewById(R.id.tvaccFname);
        TextView tvaccLname = (TextView) view.findViewById(R.id.tvaccLname);
        TextView tvdisplayName = (TextView) view.findViewById(R.id.tvaccDisplayname);
        TextView tvemail = (TextView) view.findViewById(R.id.tvEmail);
        TextView tvspecAccId = (TextView) view.findViewById(R.id.tvSpecId);
        Account temp = objects.get(position);
        tvaccFname.setText(temp.getFirstName());
        tvaccLname.setText(temp.getLastName());
        tvdisplayName.setText(temp.getDisplayName());
        tvemail.setText(temp.getEmail());
        tvspecAccId.setText(temp.getAccSpecId());

        return view;

    }
}
