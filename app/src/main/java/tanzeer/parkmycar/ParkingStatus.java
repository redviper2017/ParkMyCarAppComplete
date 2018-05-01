package tanzeer.parkmycar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ParkingStatus extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private DatabaseReference databaseReference;
    private ArrayList<Parking> parkingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_status);

        listView=findViewById(R.id.list_parking);
        parkingList= new ArrayList<Parking>();
        context=getApplicationContext();
        databaseReference= FirebaseDatabase.getInstance().getReference("parkings");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Parking parking = postSnapshot.getValue(Parking.class);
                    if (parking!=null){
                        parkingList.add(parking);
                    }
                }
                ParkingAdapter parkingAdapter = new ParkingAdapter(parkingList,context);
                listView.setAdapter(parkingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ParkingStatus.this,AdminPanel.class));
    }
}
