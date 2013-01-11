package edu.unca.ivandryi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;

public class ReportActivity extends Activity {
	TextView report;
	String returnString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        report = (TextView)findViewById(R.id.showResult);
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
			}
		}
        catch (Exception e) {
			Log.e("log_tag","Error in http connection!!" + e.toString());
		}
    }
}
