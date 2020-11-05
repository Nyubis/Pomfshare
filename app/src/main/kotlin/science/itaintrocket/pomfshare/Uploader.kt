package science.itaintrocket.pomfshare

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class Uploader(private val source: MainActivity, private val contentUri: Uri, private val host: Host) : AsyncTask<String?, Int?, String>() {
    private val tag = "ayy lmao"
    private val client = OkHttpClient()

    override fun doInBackground(vararg params: String?): String {
        val filename = params[0]
        val contentType = params[1]
        val uploadUrl = host.url
        val fieldName = if (host.type === Host.Type.POMF) "files[]" else "file"
        val result: String
        try {
            val formBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(fieldName, filename,
                            ContentUriRequestBody(source.contentResolver, contentUri))
                    .build()
            val request = Request.Builder().url(uploadUrl!!).post(formBody).build()
            val response = client.newCall(request).execute()
            Log.d(tag, String.format("%d: %s", response.code, response.message))
            result = response.body!!.string()
            Log.d(tag, result)
        } catch (e: IOException) {
            Log.e(tag, e.message!!)
            return String.format("Upload failed, check your internet connection (%s)", e.message)
        }
        return extractUrl(result)
    }

    // For some reason unknown to me, a fair amount of the filehosts contain a double occurrence of their
    // domain in the result, e.g. https://example.com/https://example.com/file.jpg
    // This method extracts the correct url from such a result
    private fun extractUrl(result: String): String {
        val protocol = result.substring(0, result.indexOf(':')) // http or https?
        val index = result.lastIndexOf("$protocol://")
        return if (index > 0) {
            result.substring(index)
        } else result
    }

    override fun onPostExecute(result: String) {
        source.finishUpload(result)
    }

}