package roy.trial.opac;

import java.io.IOException;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edit = (EditText) findViewById(R.id.editText1);
		
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
		//txt.setText(myDbHelper.trial(101000));
		
	}

	public void click(View view) {
		Intent i = new Intent(MainActivity.this, ResultActivity.class);
		i.putExtra("search", edit.getText().toString());
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
