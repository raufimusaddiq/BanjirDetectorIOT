package pre.com.loraiot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DetectionActivity extends AppCompatActivity implements DetectionContract.View {
    private static final String TAG = DetectionActivity.class.getSimpleName();
    private ImageView imageData;
    private TextView textStatus, textDistance;
    private DBHelper db;
    private DataViewModel dataView;
    private DataSensor data;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imageData = findViewById(R.id.imageDetection);
        textStatus = findViewById(R.id.textDetecttion);
        textDistance = findViewById(R.id.textDistance);

        dataView = new DataViewModel();
        db = new DBHelper(this);
        data = new DataSensor();

        try{
            data = db.getData();
            imageData.setImageResource(dataView.getImage(data.distance));
            textStatus.setText(dataView.getStatus(data.distance));
            textDistance.setText(dataView.getDistanceString(data.distance));
        } catch(Exception e){

        }



        final String topics = "+/devices/+/up";
        String clientId = MqttClient.generateClientId();
        MqttCallback callBack = new MQTTCallBack(this);
        final MqttAndroidClient client = ((MQTTCallBack) callBack).createNewClient(this);

        try {
            IMqttToken token = client.connect(((MQTTCallBack) callBack).createOptions());
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Connected");
                    try {
                        client.subscribe(topics, 2);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });

            client.setCallback(callBack);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateView(int distance) {
        imageData.setImageResource(dataView.getImage(distance));
        textStatus.setText(dataView.getStatus(distance));
        textDistance.setText(dataView.getDistanceString(distance));
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.database:
                startActivity(new Intent(DetectionActivity.this,DataViewActivity.class));
                break;
            default:
                break;
        }
        return true;
    }
}