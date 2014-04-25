package roy.trial.opac;

import java.io.IOException;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

	AutoCompleteTextView textView;
	
	String selected;
	
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
		DataBaseHelper myDbHelper;
		myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			myDbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}
		
		String[] books = myDbHelper.getAll();
		
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, books);
		textView.setAdapter(adapter);
		
		textView.setOnItemClickListener(autoItemSelectedListner);
		
		
	}

	public void click(View view) {
		Intent i = new Intent(MainActivity.this, ResultActivity.class);
		if(selected != null)
			i.putExtra("search", selected);
		else
			i.putExtra("search", textView.getText().toString());
		selected = null;
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private OnItemClickListener autoItemSelectedListner = new OnItemClickListener()
	{
	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	    {
	        //extract selected
	        selected = adapter.getItem(arg2);
	    }
	};

}
