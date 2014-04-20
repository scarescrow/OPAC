package roy.trial.opac;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultActivity extends Activity {

	TableLayout tl;
	
	TableRow tr;
	
	String id, name, author;
	
	int count;
	
	Display display;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		DataBaseHelper myDbHelper;
		myDbHelper = new DataBaseHelper(this);
		
		display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		
		Intent i = getIntent();
		
		tl = (TableLayout) findViewById(R.id.main_table);
		//tl.setBackgroundColor(Color.BLACK);
		
		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		
		TextView label_sno = new TextView(this);
        label_sno.setId(20);
        label_sno.setText("ID");
        label_sno.setTextColor(Color.WHITE);
        label_sno.setGravity(Gravity.CENTER);
        label_sno.setPadding(5, 5, 5, 5);
        tr_head.addView(label_sno);
        
        TextView label_name = new TextView(this);
        label_name.setId(21);// define id that must be unique
        label_name.setText("Title"); // set the text for the header 
        label_name.setGravity(Gravity.CENTER);
        label_name.setTextColor(Color.WHITE); // set the color
        label_name.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_name); // add the column to the table row here
        
        TextView label_author = new TextView(this);
        label_author.setId(22);// define id that must be unique
        label_author.setText("Author"); // set the text for the header 
        label_author.setGravity(Gravity.CENTER);
        label_author.setTextColor(Color.WHITE); // set the color
        label_author.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_author); // add the column to the table row here
        
        tl.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
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
		
		String search = i.getStringExtra("search");
		
		
        count = 0;
		
		Cursor cursor = myDbHelper.search(search);
		
		if (cursor != null && cursor.getCount() > 0) {
	        cursor.moveToFirst();
		}
		
		while(cursor.moveToNext()) {
			id = cursor.getString(0);
			name = cursor.getString(1);
			author = cursor.getString(2);
			
			tr = new TableRow(this);
			
			tr.setId(100000 + count);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr.setMinimumWidth(display.getWidth());
			
			TextView sn_label = new TextView(this);
			sn_label.setId(2000000+count); 
			sn_label.setText(id);
			sn_label.setPadding(2, 0, 5, 0);
			sn_label.setTextColor(Color.WHITE);
			tr.addView(sn_label);
			
			TextView name_label = new TextView(this);
			name_label.setId(300000+count);
			name_label.setText(name);
			name_label.setPadding(20, 0, 0, 0);
			name_label.setTextColor(Color.WHITE);
			tr.addView(name_label);
			
			TextView author_label = new TextView(this);
			author_label.setId(400000+count);
			author_label.setText(author);
			author_label.setPadding(10, 0, 0, 0);
			author_label.setTextColor(Color.WHITE);
			tr.addView(author_label);
			tr.setClickable(true);
			tr.setOnClickListener(clicker);
			
			if(count%2!=0) {
				tr.setBackgroundColor(Color.GRAY);
			} else {
				sn_label.setTextColor(Color.BLACK);
				name_label.setTextColor(Color.BLACK);
				author_label.setTextColor(Color.BLACK);
			}
			
			//tr.setOnClickListener(clicker);
			
			tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
			
			
			count+=1;
		}
		
		if (count == 0) {
			Log.d("hello", "hello");
			tr = new TableRow(this);
			tr.setMinimumWidth(display.getWidth());
			
			
			TextView sn_label = new TextView(this);
			sn_label.setId(200+count); 
			sn_label.setText("");
			sn_label.setPadding(2, 0, 5, 0);
			tr.addView(sn_label);
			
			TextView name_label = new TextView(this);
			name_label.setId(200+count);
			name_label.setText("No Record Found");
			name_label.setPadding(20, 0, 0, 0);
			tr.addView(name_label);
			
			tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	
	public OnClickListener clicker = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String id = ((TextView)((TableRow)v).getChildAt(0)).getText().toString();
			Intent i = new Intent(ResultActivity.this, FullActivity.class);
			i.putExtra("id", id);
			startActivity(i);
		}
	};

}
