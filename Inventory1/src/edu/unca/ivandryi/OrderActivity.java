package edu.unca.ivandryi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends Activity {
	TextView report, viewOrder;
	String returnString;
	Button submitOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        report = (TextView)findViewById(R.id.showOrder);
        viewOrder = (TextView)findViewById(R.id.viewOrder);
        submitOrder = (Button)findViewById(R.id.submitOrder);
        String response = null;
        try {
			response = CustomHttpClient.executeHttpGet("http://joe.cs.unca.edu/~andryiiv/senior/report.php");
			String result = response.toString();
			if (result.equals("")) {
				report.setText("   Nothing to order  ");
			}
			else {
				try{
					returnString = "";
					JSONArray jArray = new JSONArray(result);
					for(int i=0; i<jArray.length(); i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("log_tag","item: "+json_data.getString("item")+
                                ", usedAmount: "+json_data.getInt("usedAmount"));
                        returnString += "\n" + json_data.getString("item") + " -> "+ json_data.getInt("usedAmount");
					}
				}
				catch(JSONException e){
					Log.e("log_tag", "Error parsing data "+e.toString());
				}
    
				try{
					report.setText(returnString);
				}
				catch(Exception e){
					Log.e("log_tag","Error in Display!" + e.toString());;          
				}  
				submitOrder.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String response = null;
						try {
						response = CustomHttpClient.executeHttpGet("http://joe.cs.unca.edu/~andryiiv/senior/order.php");
						String result = response.toString();
						viewOrder.setText(result);
						}
						catch (Exception e) {
							Log.e("log_tag","Error in http connection!!" + e.toString());
						}
					}
				});
			}
		}
        catch (Exception e) {
			Log.e("log_tag","Error in http connection!!" + e.toString());
		}
    }
}
