package edu.unca.ivandryi;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class ItemsActivity extends Activity implements OnItemSelectedListener {
	TextView headerF, errorF;
	Spinner spinnerF;
	EditText numberUsedF;
	Button submitF;
	String item = "";
	String number = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frozen_items);
        Intent intent = getIntent();
        String message = intent.getStringExtra(InventoryActivity.EXTRA_MESSAGE);
        headerF = (TextView)findViewById(R.id.header);
        spinnerF = (Spinner)findViewById(R.id.spinner);
    	spinnerF.setOnItemSelectedListener(this);
    	ArrayAdapter<CharSequence> adapter;
        if (message.equals("frozen")) {
        	headerF.setText("Frozen Items");
         	adapter = ArrayAdapter.createFromResource(this, R.array.frozen_items, android.R.layout.simple_spinner_item);
         	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerF.setAdapter(adapter);
        }
        if (message.equals("produce")) {
        	headerF.setText("Produce Items");
         	adapter = ArrayAdapter.createFromResource(this, R.array.produce_items, android.R.layout.simple_spinner_item);
         	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerF.setAdapter(adapter);
        }
        if (message.equals("dry")) {
        	headerF.setText("Dry Items");
         	adapter = ArrayAdapter.createFromResource(this, R.array.dry_items, android.R.layout.simple_spinner_item);
         	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerF.setAdapter(adapter);
        }
        if (message.equals("nonfood")) {
        	headerF.setText("Nonfood Items");
         	adapter = ArrayAdapter.createFromResource(this, R.array.nonfood_items, android.R.layout.simple_spinner_item);
         	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerF.setAdapter(adapter);
        }
        errorF = (TextView)findViewById(R.id.errorF);
        numberUsedF = (EditText)findViewById(R.id.numberUsed);
        submitF = (Button)findViewById(R.id.submitF); 
        
        submitF.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				number = numberUsedF.getText().toString();
				if (number.equals("") || isInteger(number) == false) {
					errorF.setText("Insert a valid number");
				} else {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    				postParameters.add(new BasicNameValuePair("item", item));
    				postParameters.add(new BasicNameValuePair("usedAmount", number));
    				String response = null;
    				try {
    					response = CustomHttpClient.executeHttpPost("http://joe.cs.unca.edu/~andryiiv/senior/itemsUpdate.php",postParameters);
            			String result = response.toString();
            			errorF.setText(result);
    				} catch (Exception e) {
    					Log.e("log_tag","Error in http connection!!" + e.toString());
    					}
				}
			}
		});
        
    }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		item = (String)spinnerF.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
	
	public boolean isInteger (String number) {
		try {
	        Integer.parseInt(number);
	        return true;
	    }
	    catch (Exception e) {
	        return false;
	    }
	}

    
}
