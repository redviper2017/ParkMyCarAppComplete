package tanzeer.parkmycar;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener{
    private TextView btnAlreadyHaveAccount;
    private Button signUp;
    private EditText userName, email, phone, password, registrationNo;
    Spinner spinner;
    String statusOfUser;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseUsers;

    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        btnAlreadyHaveAccount = findViewById(R.id.txtBtn_alreadyHaveAccount);
        signUp=findViewById(R.id.btn_signupFromSignUpPage);
        userName=findViewById(R.id.txt_usernameDuringRegistration);
        email=findViewById(R.id.txt_emailDuringRegistration);
        phone=findViewById(R.id.txt_phoneDuringRegistration);
        password=findViewById(R.id.txt_passwordDuringRegistration);
        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        registrationNo=findViewById(R.id.txt_registrationNoDuringRegistration);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseUsers= FirebaseDatabase.getInstance().getReference();
        btnAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userName.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter user name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(registrationNo.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter vehicle registration number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString().length()!=11){
                    Toast.makeText(getApplicationContext(), "Enter valid phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length()<6){
                    Toast.makeText(getApplicationContext(), "Password must be more than six charachters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                                    String id = ref.push().getKey();
                                    Users users = new Users(userName.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),
                                            registrationNo.getText().toString(),statusOfUser);
                                    ref.child(id).setValue(users);
                                    if (statusOfUser.equals("admin")){
                                        startActivity(new Intent(SignUp.this, AdminPanel.class));
                                        finish();
                                    }
                                    else {
                                        startActivity(new Intent(SignUp.this, Main.class));
                                        finish();
                                    }
                                }
                            }
                        });
            }
        });

        //Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("user");
        categories.add("admin");


        //Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);

        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        //attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        statusOfUser = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
