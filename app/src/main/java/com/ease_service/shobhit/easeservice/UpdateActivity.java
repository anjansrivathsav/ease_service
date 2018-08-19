package com.ease_service.shobhit.easeservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    private String u;
    private DatabaseReference root;
    private TextView machine_number;
    private TextView desp;
    private Button acpbtn;
    private Button endbtn;
    private FirebaseAuth mAuth;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent j = getIntent();
        u = j.getStringExtra("ID");
        root = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        machine_number = (TextView) findViewById(R.id.machine_name);
        desp = (TextView) findViewById(R.id.desp);

        DatabaseReference databaseReference_machine = FirebaseDatabase.getInstance().getReference().child(u).child("machine_number");


        databaseReference_machine.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String values = dataSnapshot.getValue(String.class);
                machine_number.setText(values);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference_desp = FirebaseDatabase.getInstance().getReference().child(u).child("description");


        databaseReference_desp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String values = dataSnapshot.getValue(String.class);
                desp.setText(values);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        acpbtn = (Button) findViewById(R.id.accptbtn);
        endbtn = (Button) findViewById(R.id.end_time);
        calendar = Calendar.getInstance();
        acpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(UpdateActivity.this,root.child("users").child(mAuth.getCurrentUser().getEmail()).toString(),Toast.LENGTH_LONG).show();
                root.child("users").child(mAuth.getCurrentUser().getEmail().replace(".","dot")).setValue("notfree");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                String val = simpleDateFormat.format(calendar.getTime()).toString();
//                String val = simpleDateFormat.toString();
                DatabaseReference databaseReference_update = FirebaseDatabase.getInstance().getReference().child(u).child("accept_time");
                databaseReference_update.setValue(val);

                DatabaseReference databaseReference_user = FirebaseDatabase.getInstance().getReference().child(u).child("assign");
                databaseReference_user.setValue(mAuth.getCurrentUser().getEmail().replace(".","dot"));
                startActivity(new Intent(UpdateActivity.this, ServiceListView.class));
                Toast.makeText(UpdateActivity.this, "Added", Toast.LENGTH_LONG).show();

            }

        });

        endbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference_user = FirebaseDatabase.getInstance().getReference().child(u).child("end_time");
                root.child("users").child(mAuth.getCurrentUser().getEmail().replace(".","dot")).setValue("free");
                SimpleDateFormat simpleDateFormatnew = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String val2 = simpleDateFormatnew.format(calendar.getTime()).toString();
                databaseReference_user.setValue(val2);
                startActivity(new Intent(UpdateActivity.this, ServiceListView.class));
                Toast.makeText(UpdateActivity.this, "Work finished", Toast.LENGTH_LONG).show();
            }
        });


    }
}
