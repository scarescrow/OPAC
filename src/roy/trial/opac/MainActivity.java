package roy.trial.opac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.SQLException;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	AutoCompleteTextView textView;
	
	String selected;
	
	ArrayAdapter<String> adapter;
	
	private Button mbtSpeak;
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (AutoCompleteTextView) findViewById(R.id.Auto);
		DataBaseHelper myDbHelper;
		mbtSpeak = (Button) findViewById(R.id.button1);
		checkVoiceRecognition();
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
		textView.setThreshold(1);
		textView.setAdapter(adapter);
		textView.setOnItemClickListener(autoItemSelectedListner);
		
		
	}
	
	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			mbtSpeak.setEnabled(false);
			mbtSpeak.setText("Voice recognizer not present");
			Toast.makeText(this, "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
		}
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
	
	public void speak(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		// Specify the calling package to identify your application
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());

		// Given an hint to the recognizer about what the user is going to say
		// There are two form of language model available
		// 1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
		// 2.LANGUAGE_MODEL_FREE_FORM : If not sure about the words or phrases
		// and its domain.
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

		// If number of Matches is not selected then return show toast message

		int noOfMatches = 1;
		// Specify how many results you want to receive. The results will be
		// sorted where the first result is the one with higher confidence.
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		// Start the Voice recognizer activity for the result.
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

			// If Voice recognition is successful then it returns RESULT_OK
			if (resultCode == RESULT_OK) {

				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if (!textMatchList.isEmpty()) {
					textView.setText(textMatchList.get(0));
					selected = textMatchList.get(0);
				}
				// Result code for various error.
			}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
