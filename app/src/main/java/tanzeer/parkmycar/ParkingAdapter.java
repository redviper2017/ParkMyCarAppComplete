package tanzeer.parkmycar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tanze on 4/16/2018.
 */

public class ParkingAdapter extends ArrayAdapter<Parking> {

    private List<Parking> parkingList;
    private Context context;
    String TAG = "ParkingAdapter.";

    public ParkingAdapter(List<Parking> parkingList, Context ctx){
        super(ctx,R.layout.rows,parkingList);
        this.parkingList=parkingList;
        this.context=ctx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.rows,parent,false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.textView1);
        TextView capacity = (TextView)convertView.findViewById(R.id.textView2);
        TextView free = (TextView)convertView.findViewById(R.id.textView4);


        Parking parking = parkingList.get(position);

        name.setText("Shopping Mall: "+parking.getName());
        capacity.setText("Total Capacity: "+parking.getCapacity());
        free.setText("Free Spaces left: "+parking.getFree().toString());
        return convertView;

    }
}
