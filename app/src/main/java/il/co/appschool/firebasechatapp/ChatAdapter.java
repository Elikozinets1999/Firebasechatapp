package il.co.appschool.firebasechatapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by elili on 3/10/2018.
 */

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    Context context;
    List<ChatMessage> objects;

    //Builds a new chat adapter.
    public ChatAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<ChatMessage> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    //Defines views of the adapter.
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.message,parent,false);

        TextView tvSender = view.findViewById(R.id.message_user);

        TextView tvBody = view.findViewById(R.id.message_text);
        TextView tvTime = view.findViewById(R.id.message_time);
        ChatMessage temp = objects.get(position);

        tvSender.setText(String.valueOf(temp.getMessageUser()));
        String[] sender = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().split(" ");
        String Sender = sender[1]+" "+sender[2];
        if(tvSender.getText().equals(Sender)){
            tvSender.setTextColor(Color.BLUE);
            tvSender.setText("You");
        }
        else
            tvSender.setTextColor(Color.BLACK);
        tvBody.setText(String.valueOf(temp.getMessageText()));
        tvTime.setText(String.valueOf(temp.getFormattedMessageTime()));

        return view;
    }
}
