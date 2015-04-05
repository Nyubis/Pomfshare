package science.itaintrocket.pomfshare;

import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;


public class PostToPomf extends AsyncTask<String, Integer, String>{
	private final String pomfurl = "http://pomf.se/upload.php";
	private final String boundary = "*****";
	private final int maxBufferSize = 1024*1024;
	private final String tag = "ayy lmao";
	private final String extensionguess = "jpg";
	
	private FileDescriptor file;
	private MainActivity source;

	public PostToPomf(MainActivity sender, FileDescriptor fd) {
		this.source = sender;
		this.file = fd;
	}
	
	@Override
	protected String doInBackground(String... params) {
		String filename = params[0];
		String contentType = params[1];
		String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType);
		if (extension == null) {
			extension = extensionguess; // MimeTypeMap can't always infer the extension correctly
		}
		
		String result = null;
		try {
	
		    HttpURLConnection conn = (HttpURLConnection) new URL(pomfurl).openConnection();
		 
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setUseCaches(false);
		 
		    conn.setRequestMethod("POST");
		 
		    conn.setRequestProperty("Connection", "Keep-Alive");
		    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
		 
		    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		    out.writeBytes("--" + boundary + "\r\n");
		    out.writeBytes(String.format("Content-Disposition: form-data; name=\"files[]\";filename=\"%s.%s\"\r\nContent-type: %s\r\n",
		    		filename, extension, contentType));
		    out.writeBytes("\r\n");
		    
		    Log.d(tag, filename + "." + extension);
		    
			FileInputStream fileInputStream = new FileInputStream(file);
		 
		    int bytesAvailable = fileInputStream.available();
		    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
		    byte[] buffer = new byte[bufferSize];
		 
		    Log.d(tag, "Pre-read file " + fileInputStream);
		    // Read file
		    int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		 
		    while (bytesRead > 0)
		    {
		        out.write(buffer, 0, bytesRead);
		        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		    }

		    Log.d(tag, "Post-read file");
		    out.writeBytes("\r\n");
		    out.writeBytes("--" + boundary + "--\r\n");
		 
		    // Responses from the server (code and message)
		    int responseCode = conn.getResponseCode();
		    String responseMessage = conn.getResponseMessage();
		    
		    Scanner reader = new Scanner(conn.getInputStream());
		    result = reader.nextLine();

		    Log.d(tag, String.format("%d: %s", responseCode, responseMessage));
		    Log.d(tag, result);		    
		 
		    fileInputStream.close();
		    reader.close();
		    out.flush();
		    out.close();
		    
		} catch(IOException e) {
			Log.e(tag, e.getMessage());
			return "Upload failed, check your internet connection";
		}

	    return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		source.finishUpload(result);
	}
	

}