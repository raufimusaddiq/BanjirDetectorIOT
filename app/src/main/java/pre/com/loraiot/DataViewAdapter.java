package pre.com.loraiot;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DataViewAdapter extends RecyclerView.Adapter<DataViewAdapter.ViewHolder> {
    private List<DataSensor> dataSensors;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textTimeStamp, textDistance;

        ViewHolder(View view){
            super(view);
            textTimeStamp = view.findViewById(R.id.textTimesStampAdapter);
            textDistance = view.findViewById(R.id.textDistanceAdapter);
        }
    }

    public DataViewAdapter(List<DataSensor> dataSensors){
        this.dataSensors = dataSensors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_adapter, parent, false);

        return new DataViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataSensor dataSensor = dataSensors.get(position);
        holder.textTimeStamp.setText(dataSensor.timeStamp);
        holder.textDistance.setText(String.valueOf(dataSensor.distance));
    }

    @Override
    public int getItemCount() {
        return dataSensors.size();
    }

}
