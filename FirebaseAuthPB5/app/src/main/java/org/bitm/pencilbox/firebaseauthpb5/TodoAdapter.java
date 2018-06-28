package org.bitm.pencilbox.firebaseauthpb5;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<MyTodoList> {
    private Context context;
    private List<MyTodoList>lists;
    public TodoAdapter(@NonNull Context context,List<MyTodoList>lists) {
        super(context, R.layout.todo_row, lists);
        this.context = context;
        this.lists = lists;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.todo_row,parent,false
        );
        TextView todoTV = convertView.findViewById(R.id.row_todoTV);
        TextView timeTV = convertView.findViewById(R.id.row_todoTime);
        todoTV.setText(lists.get(position).getTodo());
        timeTV.setText(lists.get(position).getDate());
        return convertView;
    }
}
