package science.itaintrocket.pomfshare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListAdapter
import android.widget.ListView
import java.util.*


class HostListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hostlist)

        // This should probably be stored in a proper format at some point, but cba now
        val hosts: MutableList<Host> = ArrayList()
        hosts.add(Host("Pomf.cat", "https://pomf.cat/upload.php?output=gyazo", "75MiB", Host.Type.POMF))
        hosts.add(Host("SICP", "http://sicp.me/", "25MiB", Host.Type.UGUU))
        hosts.add(Host("Uguu", "https://uguu.se/api.php?d=upload-tool", "100MiB, 24 hours", Host.Type.UGUU))

        val listView = findViewById<ListView>(R.id.host_list_view)
        val adapter: ListAdapter = HostListAdapter(this, hosts)
        listView.adapter = adapter
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val data = Intent()
            data.putExtra("Host", hosts[position].toBundle())
            setResult(RESULT_OK, data)
            super.finish()
        }
    }
    //	@Override
    //	protected void onListItemClick(ListView l, View v, int position, long id) {
    //		Intent data = new Intent();
    //		data.putExtra("Host", hosts.get(position).toBundle());
    //		setResult(RESULT_OK, data);
    //		super.finish();
    //	}
}