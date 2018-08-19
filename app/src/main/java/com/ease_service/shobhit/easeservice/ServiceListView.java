package com.ease_service.shobhit.easeservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ServiceListView extends AppCompatActivity {

    private Button add_machine;
    private ListView simpleList;
    private DatabaseReference root;
    List<MachineInfo> machineInfoList;
    private Button userlist;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        setTitle("View");
        setContentView(R.layout.activity_service_list_view);
        add_machine = (Button) findViewById(R.id.add_btn);

        add_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServiceListView.this, ListActivity.class));
                finish();
            }
        });
//        Toast.makeText(ServiceListView.this,root.getDatabase().toString(), Toast.LENGTH_SHORT).show();
        root = FirebaseDatabase.getInstance().getReference();
        machineInfoList = new ArrayList<>();

        simpleList = (ListView) findViewById(R.id.simpleListView);
        userlist  = (Button) findViewById(R.id.userlist);
        userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServiceListView.this,UserServiceList.class));
            }
        });
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(ServiceListView.this, LoginActivity.class));

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        final String[] key= new String[10000];
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                machineInfoList.clear();
                int k =0;
                for (DataSnapshot machineSnapshot : dataSnapshot.getChildren()) {
                    MachineInfo post = machineSnapshot.getValue(MachineInfo.class);
                    machineInfoList.add(post);
                    key[k++] = machineSnapshot.getKey();
                }
                MachineList adapter = new MachineList(ServiceListView.this, machineInfoList);
                simpleList.setAdapter(adapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String sub = key[position];
//                        Toast.makeText(ServiceListView.this, key[position], Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ServiceListView.this, UpdateActivity.class);
                        i.putExtra("ID", sub);
                        startActivity(i);
                    }

                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
