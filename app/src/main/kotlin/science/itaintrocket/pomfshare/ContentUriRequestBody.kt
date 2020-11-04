package science.itaintrocket.pomfshare

import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.IOException

// From https://cketti.de/2020/05/23/content-uris-and-okhttp/
class ContentUriRequestBody(
        private val contentResolver: ContentResolver,
        private val contentUri: Uri
) : RequestBody() {

    override fun contentType(): MediaType? {
        val contentType = contentResolver.getType(contentUri)
        return contentType?.toMediaTypeOrNull()
    }

    override fun writeTo(sink: BufferedSink) {
        val inputStream = contentResolver.openInputStream(contentUri)
                ?: throw IOException("Couldn't open content URI for reading")

        inputStream.source().use { source ->
            sink.writeAll(source)
        }
    }
}
