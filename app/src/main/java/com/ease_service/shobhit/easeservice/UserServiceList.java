package com.ease_service.shobhit.easeservice;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserServiceList extends AppCompatActivity {

    private Button add_machine;
    private ListView simpleList;
    private DatabaseReference root;
    List<MachineInfo> machineInfoList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference rootval = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_service_list);
        add_machine = (Button) findViewById(R.id.add_btn);

        add_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserServiceList.this, ListActivity.class));
                finish();
            }
        });
//        Toast.makeText(ServiceListView.this,root.getDatabase().toString(), Toast.LENGTH_SHORT).show();
        root = FirebaseDatabase.getInstance().getReference();
        machineInfoList = new ArrayList<>();

        simpleList = (ListView) findViewById(R.id.simpleListView);

    }

    @Override
    protected void onStart() {
        super.onStart();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                machineInfoList.clear();
                for (final DataSnapshot machineSnapshot : dataSnapshot.getChildren()) {

                    MachineInfo post = machineSnapshot.getValue(MachineInfo.class);
//                    machineInfoList.add(post);
                    DatabaseReference databaseReference_status = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getEmail().replace(".", "dot"));
                    final String[] status_value = new String[1];

                    databaseReference_status.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            status_value[0] = dataSnapshot.getValue(String.class);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    machineInfoList.add(post);

                    try {
                        if (post.assign.equals(mAuth.getCurrentUser().getEmail().replace(".", "dot"))) {

                            machineInfoList.add(post);
                        }
                        if (post.assign.equals("none") && status_value[0].equals("free")) {

                            machineInfoList.add(post);
                            new CountDownTimer(60100, 1000) {
                                @Override
                                public void onTick(long l) {

                                    Toast.makeText(UserServiceList.this, "timer started", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFinish() {
                                    machineInfoList.clear();
                                    machineSnapshot.getRef().child("addlist").setValue(true);
                                    onStart();

                                }

                            };

                        }

                    } catch (Exception e) {
                        continue;
                    }


                }
                MachineList adapter = new MachineList(UserServiceList.this, machineInfoList);

                simpleList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
