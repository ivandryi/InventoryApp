package edu.unca.ivandryi;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class LookUpActivity extends Activity implements OnItemSelectedListener {
	TextView showItem;
	Spinner spinner;
	Button button;
	String item = "";
	String returnString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);
        showItem = (TextView)findViewById(R.id.showItem);
        spinner = (Spinner)findViewById(R.id.spinnerItem);
        button = (Button)findViewById(R.id.submitItem);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
     	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("item", item));
				String response = null;
				try {
					returnString = "";
					response = CustomHttpClient.executeHttpPost("http://joe.cs.unca.edu/~andryiiv/senior/lookup.php", postParameters);
					String result = response.toString();
					try{
		                JSONArray jArray = new JSONArray(result);
		                for(int i=0; i<jArray.length(); i++){
		                        JSONObject json_data = jArray.getJSONObject(i);
		                        Log.i("log_tag","item: "+json_data.getString("item")+
		                                ", instock: "+json_data.getInt("instock")+
		                                ", neededAmount: "+json_data.getInt("neededAmount"));
		                        returnString += "\n" + json_data.getString("item") + " -> " + "in stock: "+ json_data.getInt("instock") + " need: " + json_data.getInt("neededAmount");
		                }
					}
					catch(JSONException e){
		                Log.e("log_tag", "Error parsing data "+e.toString());
					}
		    
					try{
						showItem.setText(returnString);
					}
					catch(Exception e){
						Log.e("log_tag","Error in Display!" + e.toString());;          
					}   
				} 
		        catch (Exception e) {
					Log.e("log_tag","Error in http connection!!" + e.toString());
				}
				
			}
		});
    }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		item = (String)spinner.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	} 

  
}
