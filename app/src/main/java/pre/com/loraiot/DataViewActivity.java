package pre.com.loraiot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class DataViewActivity extends AppCompatActivity {

    private RecyclerView datarecycleview;
    private RecyclerView.Adapter mAdapterData;
    private RecyclerView.LayoutManager mLayoutManagerData;
    private List<DataSensor> dataList = new ArrayList<>();
    private DBHelper db;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new DBHelper(this);
        dataList.addAll(db.getAllData());

        datarecycleview = findViewById(R.id.datarecyleview);
        mAdapterData = new DataViewAdapter(dataList);
        mLayoutManagerData = new LinearLayoutManager(getApplicationContext());
        datarecycleview.setLayoutManager(mLayoutManagerData);
        datarecycleview.setItemAnimator(new DefaultItemAnimator());
        datarecycleview.setAdapter(mAdapterData);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DetectionActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

}
