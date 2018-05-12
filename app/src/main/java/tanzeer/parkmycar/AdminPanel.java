package tanzeer.parkmycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPanel extends AppCompatActivity {

    private EditText name,capacity,lat,lon;
    private Button addButton, viewBookingButton, viewParkingStatusButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewParkingStatusButton = findViewById(R.id.btn_currentParkingStatus);
        viewParkingStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanel.this,ParkingStatus.class);
                startActivity(intent);
            }
        });
        viewBookingButton = findViewById(R.id.btn_currentBookings);
        viewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this,AllParkingBookings.class));
            }
        });
        name = findViewById(R.id.txt_name);
        capacity = findViewById(R.id.txt_capacity);
        lat = findViewById(R.id.txt_lat);
        lon = findViewById(R.id.txt_lon);


        addButton = findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String n = name.getText().toString();
                final String ca = capacity.getText().toString();
                final String la = lat.getText().toString().trim();
                final String lo = lon.getText().toString().trim();
                final String free = ca;

                if (TextUtils.isEmpty(n)){
                    Toast.makeText(getApplicationContext(),"Must enter parking name!!",Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(ca)){
                    Toast.makeText(getApplicationContext(),"Must enter parking capacity!!",Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(la)){
                    Toast.makeText(getApplicationContext(),"Must enter parking coordinates!!",Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(lo)){
                    Toast.makeText(getApplicationContext(),"Must enter parking coordinates!!",Toast.LENGTH_SHORT).show();
                }

                databaseReference= FirebaseDatabase.getInstance().getReference("parkings");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int serialCurrent = Integer.parseInt(String.valueOf(dataSnapshot.getChildrenCount()))+1;
                        AddParking addParking = new AddParking(n,ca,la,lo,free,String.valueOf(serialCurrent));
                        databaseReference.child(String.valueOf(serialCurrent)).setValue(addParking);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                name.setText(null);
                capacity.setText(null);
                lat.setText(null);
                lon.setText(null);

            }
        });

        viewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(AdminPanel.this,AllParkingBookings.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminPanel.this,AdminPanel.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            new AlertDialog.Builder(AdminPanel.this)
                    .setTitle("Exit App Dialog")
                    .setMessage("Do you really want to exit the app?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
}
