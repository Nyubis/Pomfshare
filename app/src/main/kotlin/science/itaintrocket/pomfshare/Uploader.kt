package science.itaintrocket.pomfshare

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.webkit.MimeTypeMap
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class Uploader(private val source: MainActivity, private val contentUri: Uri, private val host: Host) : AsyncTask<String?, Int?, String>() {
    private val tag = "ayy lmao"
    private val client = OkHttpClient()

    override fun doInBackground(vararg params: String?): String {
        val filename = params[0]!!
        val contentType = params[1]!!
        val uploadFilename = addExtension(filename, contentType)
        val uploadUrl = host.url
        val fieldName = if (host.type === Host.Type.POMF) "files[]" else "file"
        Log.d(tag, "filename: '${filename}', contentType: '${contentType}', uploadFilename: '${uploadFilename}', "
                + "uploadUrl: '${uploadUrl}', fieldName: '${fieldName}'")
        val result: String
        try {
            val formBodyBuilder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(fieldName, uploadFilename, ContentUriRequestBody(source.contentResolver, contentUri))
            if (host.authRequired == true) {
                host.authKey?.let { formBodyBuilder.addFormDataPart("uuid", it) }
            }
            val formBody: RequestBody = formBodyBuilder.build()
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

    // Due to how the sharing works, the filename may not include an extension, but some of the hosts rely on the
    // extension to infer mimetype, so if our file doesn't have an extension, we add one that's appropriate for the
    // mimetype.
    private fun addExtension(filename: String, contentType: String): String {
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType)
        if (extension != null && filename.endsWith(extension, ignoreCase = true)) {
            return filename
        }
        return "$filename.$extension"
    }

    override fun onPostExecute(result: String) {
        source.finishUpload(result)
    }

}