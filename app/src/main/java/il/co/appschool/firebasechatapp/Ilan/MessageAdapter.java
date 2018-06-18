package il.co.appschool.firebasechatapp.Ilan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import il.co.appschool.firebasechatapp.R;

/**
 * Created by ailan on 03/04/2018.
 */

public class MessageAdapter extends ArrayAdapter {

    Context context;
    List<Message> objects;
    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);

        TextView tvmessage =(TextView) view.findViewById(R.id.tvmessage);
        TextView tvmsgSaveDay =(TextView) view.findViewById(R.id.tvmsgSaveDay);
        ImageView iv = (ImageView)view.findViewById(R.id.iv);
        TextView tvmsgSaveHour = (TextView) view.findViewById(R.id.tvmsgSaveHour);
        TextView tvfromWhichChat = (TextView) view.findViewById(R.id.tvfromWhichChat);
        Message temp = objects.get(position);
        tvmessage.setText(temp.getMessage());
        tvmsgSaveDay.setText(temp.getMsgSaveDay());
        tvmsgSaveHour.setText(temp.getMsgSaveHour());
        tvfromWhichChat.setText(temp.getFromWhichChat());
        //iv.setImageResource(R.mipmap.green);
        return view;
    }
}