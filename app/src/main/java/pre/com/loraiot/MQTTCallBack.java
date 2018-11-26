package pre.com.loraiot;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MQTTCallBack implements MqttCallback {
    private final static String TAG = MQTTCallBack.class.getSimpleName();

    private static final String username = "app_group01";
    private static final String password = "ttn-account-v2.7WFZ0HRfqZLbPebdN1ym7WWctCytAfScbSYL2k_2lKU";
    private final String topic = "+/device/+/up";
    private Context context;

    private DetectionContract.View view;
    private DBHelper dbHelper;

    public MQTTCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection to MQTT broker lost!");
        final MqttAndroidClient client = createNewClient(view);
        MqttConnectOptions options = createOptions();
        try {
            if (client != null) {
                IMqttToken token = client.connect(options);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.d(TAG, "Connected");
                        try {
                            client.subscribe(topic, 2);
                        } catch (MqttException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.e(TAG, exception.getMessage());
                    }
                });
                client.setCallback(this);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        System.out.println("Message received from things:\n\t"+ new String(message.getPayload()));
//        String dataSensor = message.getPayload().toString();

        JSONObject response = new JSONObject(new String(message.getPayload()));
        JSONObject payloadFields = response.getJSONObject("payload_fields");
        int distance = payloadFields.getInt("distance");
        Log.d(TAG, "This is distance :" + String.valueOf(distance));
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        dbHelper.insertData(sdfDate.format(new Date()), distance);

        view.updateView(distance);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public MqttAndroidClient createNewClient(DetectionContract.View view) {
        this.view = view;
        dbHelper = new DBHelper(context);
        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =  new MqttAndroidClient(context, "tcp://asia-se.thethings.network", clientId);
        return client;
    }

    public MqttConnectOptions createOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        return options;
    }
}
