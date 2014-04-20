package roy.trial.opac;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.view.Menu;
import android.widget.TextView;
import roy.trial.opac.DataBaseHelper;;

public class FullActivity extends Activity {

	TextView id, name, author, year, shelf, publisher, copies;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full);
		
		Intent i = getIntent();
		
		int s_id = Integer.parseInt(i.getStringExtra("id"));
		
		book b;
		
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
		
		b = myDbHelper.search(s_id);
		
		id = (TextView) findViewById(R.id.id);
		name = (TextView) findViewById(R.id.title);
		author = (TextView) findViewById(R.id.author);
		year = (TextView) findViewById(R.id.year);
		shelf = (TextView) findViewById(R.id.shelf);
		publisher = (TextView) findViewById(R.id.publisher);
		copies = (TextView) findViewById(R.id.copies);
		
		id.setText(b.id());
		name.setText(b.name());
		author.setText(b.author());
		year.setText(b.year());
		shelf.setText(b.shelf());
		publisher.setText(b.publisher());
		copies.setText(b.copies());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full, menu);
		return true;
	}

}
