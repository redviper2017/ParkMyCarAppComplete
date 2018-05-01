package tanzeer.parkmycar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllParkingBookings extends AppCompatActivity {
    private ArrayList<Bookings> bookingList;
    private ListView listView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_parking_bookings);
        listView=findViewById(R.id.list_booking);
        bookingList= new ArrayList<Bookings>();
        context=getApplicationContext();
        DatabaseReference allBookingData = FirebaseDatabase.getInstance().getReference("bookings");
        allBookingData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Bookings bookings = postSnapshot.getValue(Bookings.class);
                    if (bookings!=null){
                        bookingList.add(bookings);
                    }
                }
                BookingAdapter bookingAdapter = new BookingAdapter(bookingList,context);
                listView.setAdapter(bookingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AllParkingBookings.this,AdminPanel.class));
    }
}
