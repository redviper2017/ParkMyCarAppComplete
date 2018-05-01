package tanzeer.parkmycar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tanze on 4/27/2018.
 */

public class BookingAdapter extends ArrayAdapter {
    private List<Bookings> bookingList;
    private Context context;
    String TAG = "BookingAdapter";
    public BookingAdapter(List<Bookings> bookingList, Context ctx){
        super(ctx,R.layout.rows1,bookingList);
        this.bookingList=bookingList;
        this.context=ctx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.rows1,parent,false);
        }
        TextView name = convertView.findViewById(R.id.txt_nameCustomer);
        TextView phone = convertView.findViewById(R.id.txt_phoneNumber);
        TextView vin = convertView.findViewById(R.id.txt_vehicleNumber);
        TextView start = convertView.findViewById(R.id.txt_startT);
        TextView end = convertView.findViewById(R.id.txt_endT);
        TextView parkingBooked = convertView.findViewById(R.id.txt_parkingBooked);

        Bookings bookings = bookingList.get(position);
        name.setText(bookings.getName());
        phone.setText(bookings.getMobileNumber());
        vin.setText(bookings.getVehicleNumber());
        start.setText(bookings.getFrom());
        end.setText(bookings.getTo());
        parkingBooked.setText(bookings.getParking());
        return convertView;
    }
}
