package com.ease_service.shobhit.easeservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


class MachineInfo {
    public String machine_number;
    public String description;
    public String accept_time;
    public String end_time;
    public String assign;
    public Boolean addlist;


    public  MachineInfo(){

    }

    public MachineInfo(String machine_number, String description,String accept_time,String end_time) {
        this.machine_number = machine_number;
        this.description = description;
        this.accept_time = accept_time;
        this.end_time = end_time;
        this.assign="none";
        this.addlist = false;
    }


}

public class ListActivity extends AppCompatActivity {

    private EditText machine_name;
    private EditText description;
//    private Button add_machine;
    private Button service;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    NotificationCompat.Builder notifications;
    private  static  final  int uniqueId = 45612;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        machine_name = (EditText) findViewById(R.id.machine_text);
        description = (EditText) findViewById(R.id.desp_text);
//        add_machine = (Button) findViewById(R.id.add_btn);
        service = (Button) findViewById(R.id.service_list);
        notifications = new NotificationCompat.Builder(this);
        notifications.setAutoCancel(true);
//        add_machine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String machine_number = machine_name.getText().toString();
//                String desp = description.getText().toString();
//                MachineInfo machine_info = new MachineInfo(machine_number, desp,"","");
//                root.push().setValue(machine_info);
//                startActivity(new Intent(ListActivity.this, ServiceListView.class));
//                Toast.makeText(ListActivity.this, "added", Toast.LENGTH_SHORT).show();
//                notifications.setTicker("This is the ticker");
//                notifications.setWhen(System.currentTimeMillis());
//                notifications.setContentTitle("here is the title");
//                notifications.setContentText("Iam  the body of your notification");
//
//                Intent intent  = new Intent(this, ListActivity.class);
//                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//                notifications.setContentIntent(pendingIntent);
//
//                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                nm.notify(uniqueId,notifications.build());
//                finish();
//            }
//        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, ServiceListView.class));
            }
        });


    }

    public void adding(View view){
        String machine_number = machine_name.getText().toString();
        String desp = description.getText().toString();
        MachineInfo machine_info = new MachineInfo(machine_number, desp,"","");
        root.push().setValue(machine_info);
        startActivity(new Intent(ListActivity.this, ServiceListView.class));
        Toast.makeText(ListActivity.this, "added", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_1";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }


}
