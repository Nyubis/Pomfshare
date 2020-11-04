package science.itaintrocket.pomfshare;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HostListActivity extends ListActivity {
	private List<Host> hosts;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// This should probably be stored in a proper format at some point, but cba now
		hosts = new ArrayList<>();
		hosts.add(new Host("Pomf.cat", "http://pomf.cat/upload.php?output=gyazo", "75MiB", Host.Type.POMF));
		hosts.add(new Host("SICP", "http://sicp.me/", "25MiB", Host.Type.UGUU));
		hosts.add(new Host("Uguu", "https://uguu.se/api.php?d=upload-tool", "100MiB, 24 hours", Host.Type.UGUU));

		ListAdapter adapter = new HostArrayAdapter(this, hosts.toArray(new Host[0]));

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent data = new Intent();
		data.putExtra("Host", hosts.get(position).toBundle());
		setResult(RESULT_OK, data);
		super.finish();
	}
}
