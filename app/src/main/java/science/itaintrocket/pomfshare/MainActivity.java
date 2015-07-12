package science.itaintrocket.pomfshare;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class MainActivity extends Activity {
	private final String tag = "ayy lmao";
	private String result;
	private Button copyButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent i = new Intent(this, HostListActivity.class);
		startActivity(i);

		copyButton = (Button) findViewById(R.id.copyButton);
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

			Host uguu = new Host("Uguu", "http://uguu.se/api.php?d=upload", "100MB, 24 hours", Host.Type.UGUU);

			new Uploader(this, inputPFD, uguu).execute(imageUri.getLastPathSegment(), cr.getType(imageUri));
		}
	}
	
	public void finishUpload(String resultUrl) {
		EditText output = (EditText) findViewById(R.id.outputField);
		output.setText(resultUrl);
		// set for clipboard
		result = resultUrl;
		copyButton.setEnabled(true);
		copyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				copyToClipboard();
			}
		});
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void copyToClipboard() {
		if (result == null)
			return;
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("Upload url", result);
		clipboard.setPrimaryClip(clip);
		Toast t = Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT);
		t.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
