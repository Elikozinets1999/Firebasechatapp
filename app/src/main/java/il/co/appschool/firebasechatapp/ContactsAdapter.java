package il.co.appschool.firebasechatapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by elili on 3/20/2018.
 */

public class ContactsAdapter extends ArrayAdapter<Contact>{
    Context context;
    List<Contact> objects;

    //Creates a new Contacts adapter.
    public ContactsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Contact> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    //Defines the views of the adapter.
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        @SuppressLint({"ViewHolder", "ResourceType"}) View view = layoutInflater.inflate(R.layout.contact, parent, false);
        TextView tvEmail = view.findViewById(R.id.tvContactEmail);
        TextView tvName = view.findViewById(R.id.tvContactName);
        Contact temp = objects.get(position);

        tvEmail.setText(String.valueOf(temp.getEmail()));
        tvName.setText(String.valueOf(temp.getFullName()));
        return view;
    }
}
