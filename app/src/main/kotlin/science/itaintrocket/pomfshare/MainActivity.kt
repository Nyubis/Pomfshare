package science.itaintrocket.pomfshare

import android.annotation.TargetApi
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class MainActivity : Activity() {
    private val tag = "ayy lmao"
    private lateinit var copyButton: Button
    private lateinit var openButton: Button
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val i = Intent(this, HostListActivity::class.java)
        startActivityForResult(i, CHOOSE_HOST)
        copyButton = findViewById<View>(R.id.copyButton) as Button
        openButton = findViewById<View>(R.id.openButton) as Button
        imageUri = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as Uri
    }

    private fun displayAndUpload(host: Host) {
        val cr = contentResolver
        val view = findViewById<View>(R.id.sharedImageView) as ImageView
        view.setImageURI(imageUri)
        Uploader(this, imageUri, host).execute(imageUri.lastPathSegment, cr.getType(imageUri))
    }

    fun finishUpload(resultUrl: String?) {
        val output = findViewById<View>(R.id.outputField) as EditText
        output.setText(resultUrl)
        // set for clipboard
        copyButton.isEnabled = true
        copyButton.setOnClickListener { copyToClipboard(resultUrl) }
        openButton.isEnabled = true
        openButton.setOnClickListener { openInBrowser(resultUrl) }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun copyToClipboard(url: String?) {
        if (url == null) {
            return
        }
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Upload url", url)
        clipboard.setPrimaryClip(clip)
        val t = Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT)
        t.show()
    }

    private fun openInBrowser(url: String?) {
        if (url == null) {
            return
        }
        val uri: Uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CHOOSE_HOST && resultCode == RESULT_OK) {
            val chosen = Host(data.getBundleExtra("Host")!!)
            Log.d(tag, "Chose " + chosen.name)
            displayAndUpload(chosen)
        } else {
            // User cancels
            Log.d(tag, "Canceled")
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    companion object {
        private const val CHOOSE_HOST = 1
    }
}