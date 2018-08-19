package com.ease_service.shobhit.easeservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//class User {
//
//    public String email;
//
//    public Boolean status;
//
//    public User() {
//        // Default constructor required for calls to DataSnapshot.getValue(User.class)
//    }
//
//    public User(String email) {
//
//        this.email = email;
//        this.status = true;
//    }
//
//}


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference root;
    Button register;
    Button signin;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();


        final EditText email = (EditText) findViewById(R.id.userone);
        final EditText password = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailval = email.getText().toString();
                String passwordval = password.getText().toString();
//                User u = new User(emailval);
                root.child("users").child(emailval.replace(".","dot")).setValue("free");
                dddd(emailval,passwordval);
            }
        });
        signin = (Button)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });

    }


    public void dddd(String emailval,String passwordval) {

        mAuth.createUserWithEmailAndPassword(emailval, passwordval)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, ServiceListView.class));
//                                updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }


                    }
                });

    }
}
