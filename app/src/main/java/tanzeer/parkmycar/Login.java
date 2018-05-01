package tanzeer.parkmycar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.Map;

public class Login extends AppCompatActivity {

    private Button loginButton;
    private Button signUpButton;
    private Button learnButton;
    private EditText password;
    private FirebaseAuth mAuth;
    private EditText email;
    private static final String TAG = "LoginActivity";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email=findViewById(R.id.txt_email);
        signUpButton = findViewById(R.id.btn_signup);
        learnButton = findViewById(R.id.btn_learnMore);
        loginButton = findViewById(R.id.btn_login);
        password = findViewById(R.id.txt_password);
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
        learnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,HowTo.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String em = email.getText().toString();
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(em)){
                    Toast.makeText(getApplicationContext(),"Enter email address !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),"Enter password !",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(em,pass)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                        Toast.makeText(Login.this,getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                                }else {
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                                    db.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                                Users users = postSnapshot.getValue(Users.class);
                                                if (em.equals(users.getEmail())){
                                                    Log.d(TAG,"user status is: "+users.getStatus());
                                                    if (users.getStatus().equals("user")){
                                                        Intent intent = new Intent(Login.this,Main.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else{
                                                        Intent intent = new Intent(Login.this,AdminPanel.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        });
            }
        });
    }

}

