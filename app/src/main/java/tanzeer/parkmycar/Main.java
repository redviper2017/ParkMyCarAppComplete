package tanzeer.parkmycar;

import android.app.Dialog;

import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main extends AppCompatActivity  {

    private EditText name, mobileNumber, vehicleNumber;
    private Button startButton, stopButton, bookingButton, fareCalcButton;
    private TextView start,end,bookingCharges;
    FirebaseDatabase database;
    DatabaseReference databaseBooking;
    private static final String TAG = "Main.Activity";
    double parkingChargesBasedOnTimeSelected;
    int bookingHours;
    int bookingMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        name = findViewById(R.id.editText_name);
        mobileNumber = findViewById(R.id.editText_mobile);
        vehicleNumber = findViewById(R.id.editText_vin);
        database = FirebaseDatabase.getInstance();
        databaseBooking = database.getReference();
        bookingButton = findViewById(R.id.btn_book);
        stopButton = findViewById(R.id.btnStop);
        startButton= findViewById(R.id.btnStart);
        start=findViewById(R.id.txt_startTime);
        end=findViewById(R.id.txt_endTime);
        bookingCharges=findViewById(R.id.txt_bookingCharges);
        fareCalcButton=findViewById(R.id.btn_calculateBookingCharge);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initializing a new time picker dialog fragment
                DialogFragment dialogFragment = new TimePickerFragment();

                //show the time picker dialog fragment
                dialogFragment.show(getFragmentManager(),"Time Picker");
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initializing a new time picker dialog fragment
                DialogFragment dialogFragment = new TimePickerFragment1();

                //show the time picker dialog fragment
                dialogFragment.show(getFragmentManager(),"Time Picker");
            }
        });
        fareCalcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateTimeDifference(start.getText().toString(),end.getText().toString());
                String time = String.valueOf(bookingHours)+"."+bookingMinutes;
                double timeB = Double.parseDouble(time);
                parkingChargesBasedOnTimeSelected = timeB*30.00;
                bookingCharges.setText(parkingChargesBasedOnTimeSelected+" "+"(INR)");
            }
        });
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString().trim();
                String mN = mobileNumber.getText().toString().trim();
                String vN = vehicleNumber.getText().toString().trim();
                String sT = start.getText().toString();
                String eT = end.getText().toString();

                if (TextUtils.isEmpty(n)){
                    Toast.makeText(getApplicationContext(),"Enter your name !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mN)){
                    Toast.makeText(getApplicationContext(),"Enter your mobile number !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(vN)){
                    Toast.makeText(getApplicationContext(),"Enter your vehicle registration number !",Toast.LENGTH_SHORT).show();
                    return;
                }
                String id = databaseBooking.push().getKey();
                String parking = " ";
                Bookings booking = new Bookings(n,mN,vN,sT,eT,parking);
                databaseBooking.child("bookings").child(id).setValue(booking);
                Intent intent=new Intent(Main.this,Map.class);
                intent.putExtra("id", id);
                intent.putExtra("fees",parkingChargesBasedOnTimeSelected);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Main.this,Main.class));
    }

    public void calculateTimeDifference(String start, String end){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();
        if(difference<0)
        {
            Date dateMax = null;
            try {
                dateMax = simpleDateFormat.parse("24:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date dateMin = null;
            try {
                dateMin = simpleDateFormat.parse("00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
        }
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        Log.i("log_tag","Hours: "+hours+", Mins: "+min);
        bookingHours = hours;
        bookingMinutes = min;
    }
}




