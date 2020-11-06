package science.itaintrocket.pomfshare

import android.support.v4.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import java.util.*


class HostListActivity() : FragmentActivity(), RequestAuthenticationDialog.RequestAuthenticationDialogListener {
    private val hosts: MutableList<Host> = ArrayList()
    private lateinit var authManager: AuthManager

    private var hostRequestingAuthentication: Host? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hostlist)

        authManager = AuthManager(PreferenceManager.getDefaultSharedPreferences(applicationContext))

        // This should probably be stored in a proper format at some point, but cba now
        hosts.add(Host("Pomf.cat", "https://pomf.cat/upload.php?output=gyazo", "75MiB", Host.Type.POMF))
        hosts.add(Host("SICP", "https://sicp.me/", "25MiB", Host.Type.UGUU, authRequired = true))
        hosts.add(Host("Uguu", "https://uguu.se/api.php?d=upload-tool", "100MiB, 24 hours", Host.Type.UGUU))

        // If authentication data exists for a host, load it
        loadHostAuthentications()

        val listView = findViewById<ListView>(R.id.host_list_view)
        val adapter: ListAdapter = HostListAdapter(this, hosts)
        listView.adapter = adapter
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val selectedHost: Host = hosts[position]
            if (selectedHost.authRequired == true && authManager.findAuthKey(selectedHost) == null) {
                hostRequestingAuthentication = selectedHost
                RequestAuthenticationDialog().show(supportFragmentManager, "fragment_request_authentication")
            } else {
                val data = Intent()
                data.putExtra("Host", hosts[position].toBundle())
                setResult(RESULT_OK, data)
                super.finish()
            }
        }
    }

    override fun onDialogSubmit(dialog: DialogFragment, text: String?) {
        if (text != null) {
            hostRequestingAuthentication?.let {
                authManager.addAuthKey(it, text)
                Toast.makeText(applicationContext, "Added authentication key to ${it.name}", Toast.LENGTH_SHORT).show()
            }
        }
        loadHostAuthentications()
        hostRequestingAuthentication = null
    }

    override fun onDialogCancel(dialog: DialogFragment) {
        hostRequestingAuthentication = null
    }

    private fun loadHostAuthentications() {
        for (host in hosts) {
            if (host.authRequired == true) {
                val auth: String? = authManager.findAuthKey(host)
                auth.let { host.authKey = it }
            }
        }

    }
}