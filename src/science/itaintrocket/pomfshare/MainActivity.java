package science.itaintrocket.pomfshare;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String imageurl = "http://a.pomf.se/";
	private final String tag = "ayy lmao";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {			
			Log.d(tag, imageUri.toString());
			ImageView view = (ImageView)findViewById(R.id.sharedImageView);
			Bitmap bmp = BitmapFactory.decodeFile(imageUri.toString());
			Log.d(tag, view.toString());
			Log.d(tag, intent.getType());
			Log.d(tag, imageUri.getLastPathSegment());
			view.setImageBitmap(bmp);
			view.setImageURI(imageUri);
			
			ParcelFileDescriptor inputPFD = null; 
			try {
				inputPFD = getContentResolver().openFileDescriptor(imageUri, "r");				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			FileDescriptor fd = inputPFD.getFileDescriptor();
			
			new PostToPomf(this, fd).execute(imageUri.getLastPathSegment(), intent.getType());
		}
	}
	
	public void finishUpload(String json) {
		if (json == null)
			return;

		EditText output = (EditText) findViewById(R.id.outputField);
		
		try {
			JSONObject jo = new JSONObject(json);
			String fullurl = imageurl + jo.getJSONArray("files").getJSONObject(0).get("url");
			Log.d(tag, fullurl);
			output.setText(fullurl);
		} catch (JSONException e) {
			Log.e(tag, e.getMessage());
			Toast toast = Toast.makeText(getApplicationContext(), "Incorrect data received.", Toast.LENGTH_SHORT);
			toast.show();
			output.setText(json);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
