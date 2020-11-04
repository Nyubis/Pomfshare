package science.itaintrocket.pomfshare;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Uploader extends AsyncTask<String, Integer, String>{
	private final String boundary = "*****";
	private final int maxBufferSize = 1024*1024;
	private final String tag = "ayy lmao";
	
	private ParcelFileDescriptor file;
	private Uri contentUri;
	private ContentResolver contentResolver;
	private MainActivity source;
	private Host host;
	private OkHttpClient client = new OkHttpClient();


	public Uploader(MainActivity sender, Uri contentUri, Host host) {
		this.source = sender;
		this.contentUri = contentUri;
		this.host = host;
	}

	@Override
	protected String doInBackground(String... params) {
		String filename = params[0];
		String contentType = params[1];
		String uploadurl = host.getUrl();
		String fieldName = (host.getType() == Host.Type.POMF) ? "files[]" : "file";

		String result = null;
		try {
			RequestBody formBody = new MultipartBody.Builder()
					.setType(MultipartBody.FORM)
					.addFormDataPart(fieldName, filename,
							new ContentUriRequestBody(source.getContentResolver(), contentUri))
					.build();
			Request request = new Request.Builder().url(uploadurl).post(formBody).build();
			Response response = client.newCall(request).execute();

		    Log.d(tag, String.format("%d: %s", response.code(), response.message()));
		    result = response.body().string();
		    Log.d(tag, result);
		 
		} catch(IOException e) {
			Log.e(tag, e.getMessage());
			return String.format("Upload failed, check your internet connection (%s)", e.getMessage());
		}

	    return extractUrl(result);
	}

	// For some reason unknown to me, a fair amount of the filehosts contain a double occurrence of their
	// domain in the result, e.g. https://example.com/https://example.com/file.jpg
	// This method extracts the correct url from such a result
	private String extractUrl(String result) {
		String protocol = result.substring(0, result.indexOf(':')); // http or https?
		int index = result.lastIndexOf(protocol + "://");
		if (index > 0) {
			return result.substring(index);
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		source.finishUpload(result);
	}
	

}