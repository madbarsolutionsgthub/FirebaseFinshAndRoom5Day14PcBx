package org.bitm.pencilbox.firebaseauthpb5;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {
    private static final String TAG = DatabaseActivity.class.getSimpleName();
    private DatabaseReference rootRef;
    private DatabaseReference todoRef;
    private FirebaseUser user;
    List<MyTodoList>myTodoLists = new ArrayList<>();
    EditText todoET;
    private ListView listView;
    private TodoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        todoET = findViewById(R.id.todoET);
        listView = findViewById(R.id.todoListView);
        rootRef = FirebaseDatabase.getInstance().getReference();
        todoRef = rootRef.child("Todo");
        todoRef.keepSynced(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyTodoList myTodoList = myTodoLists.get(position);
                String todoid = myTodoList.getId();
                todoRef.child(user.getUid()).child(todoid).removeValue();
                return false;
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        todoRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myTodoLists.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    MyTodoList todoList = d.getValue(MyTodoList.class);
                    myTodoLists.add(todoList);
                }
                /*for(MyTodoList ml : myTodoLists){
                    Log.e(TAG, "todo: "+ml.getTodo());
                    Log.e(TAG, "time: "+ml.getDate());
                    Log.e(TAG, "================================");
                }*/
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {
        adapter = new TodoAdapter(this,myTodoLists);
        listView.setAdapter(adapter);
    }

    public void saveTodo(View view) {
        String todo = todoET.getText().toString();
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        String id = rootRef.child(user.getUid()).push().getKey();
        MyTodoList todoList = new MyTodoList(todo,date,id);
        todoRef.child(user.getUid()).child(id).setValue(todoList);
    }
}
