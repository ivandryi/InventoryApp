package edu.unca.ivandryi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InventoryActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "edu.unca.ivandryi.MESSAGE";
	Button frozen;
	Button produce;
	Button dry;
	Button nonfood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        
        frozen = (Button)findViewById(R.id.frozen);
        produce = (Button)findViewById(R.id.produce);
        dry = (Button)findViewById(R.id.dry);
        nonfood = (Button)findViewById(R.id.nonfood);
        
        frozen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				items("frozen");
			}
		});
        
        produce.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				items("produce");
			}
		});
        
        dry.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				items("dry");
			}
		});
        
        nonfood.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				items("nonfood");
			}
		});
        
    }
    
    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == R.id.report) {
            startActivity(new Intent(this, ReportActivity.class));
        }
        if (item.getItemId() == R.id.lookUp) {
           startActivity(new Intent(this, LookUpActivity.class));
        }
        if (item.getItemId() == R.id.order) {
            startActivity(new Intent(this, OrderActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void items(String s){
    	Intent intent = new Intent(this, ItemsActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, s);
    	startActivity(intent);
    } 
}
