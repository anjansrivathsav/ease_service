package com.ease_service.shobhit.easeservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private DatabaseReference root;
    CheckBox check;

    EditText email;
    EditText password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        root = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);


        submit = (Button) findViewById(R.id.submit);

//        User user = new User(emailval, passwordval);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailval = email.getText().toString();

                String passwordval = password.getText().toString();

                dddd(emailval,passwordval);
            }
        });


    }

    public void dddd(String emailval, String passwordval) {

        mAuth.signInWithEmailAndPassword(emailval, passwordval).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
//                           Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(new Intent(LoginActivity.this, ServiceListView.class));
                    Toast.makeText(LoginActivity.this, "Authentication Success",
                            Toast.LENGTH_LONG).show();
//                           updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
//                           Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_LONG).show();
//                           updateUI(null);
                }

            }
        });

    }

}
