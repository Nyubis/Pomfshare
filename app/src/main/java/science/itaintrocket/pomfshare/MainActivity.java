package science.itaintrocket.pomfshare;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String tag = "ayy lmao";
	private String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		ContentResolver cr = getContentResolver();
		if (imageUri != null) {			
			Log.d(tag, imageUri.toString());
			ImageView view = (ImageView)findViewById(R.id.sharedImageView);
			Bitmap bmp = BitmapFactory.decodeFile(imageUri.toString());
			Log.d(tag, cr.getType(imageUri));
			Log.d(tag, imageUri.getLastPathSegment());
			view.setImageBitmap(bmp);
			view.setImageURI(imageUri);

			ParcelFileDescriptor inputPFD = null; 
			try {
				inputPFD = cr.openFileDescriptor(imageUri, "r");				
			} catch (FileNotFoundException e) {
				Log.e(tag, e.getMessage());
				Toast toast = Toast.makeText(getApplicationContext(), "Unable to read file.", Toast.LENGTH_SHORT);
				toast.show();				
			}
			
			new Uploader(this, inputPFD, Uploader.Service.UGUU).execute(imageUri.getLastPathSegment(), cr.getType(imageUri));
		}
	}
	
	public void finishUpload(String resultUrl) {
		EditText output = (EditText) findViewById(R.id.outputField);
		output.setText(resultUrl);
		// set for clipboard
		result = resultUrl;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_copy:
				copyToClipboard();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void copyToClipboard() {
		if (result == null)
			return;
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("Upload url", result);
		clipboard.setPrimaryClip(clip);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
