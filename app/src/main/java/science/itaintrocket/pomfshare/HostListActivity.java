package science.itaintrocket.pomfshare;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HostListActivity extends ListActivity {
	private Host[] hosts;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// This should probably be stored in a proper format at some point, but cba now
		hosts = new Host[8];
		hosts[0] = new Host("Uguu", "http://uguu.se/api.php?d=upload", "100MB, 24 hours", Host.Type.UGUU);
		hosts[1] = new Host("1339", "http://1339.cf/upload.php?output=gyazo", "100MB", Host.Type.POMF);
		hosts[2] = new Host("SICP", "http://sicp.me/", "25MB", Host.Type.UGUU);
		hosts[3] = new Host("Pomf.cat", "http://pomf.cat/upload.php?output=gyazo", "50MB, Work in progress", Host.Type.POMF);
		hosts[4] = new Host("Pomf.hummingbird.moe", "http://pomf.hummingbird.moe/upload.php?output=gyazo", Host.Type.POMF);
		hosts[5] = new Host("Mixtape.moe", "https://mixtape.moe/upload.php?output=gyazo", Host.Type.POMF);
        hosts[6] = new Host("Xpo.pw", "http://xpo.pw/upload.php?output=gyazo", Host.Type.POMF);
		hosts[7] = new Host("Maxfile.ro", "https://maxfile.ro/static/upload.php?output=gyazo", Host.Type.POMF);
		hosts[8] = new Host("Bucket.pw", "http://bucket.pw/upload.php?output=gyazo", Host.Type.POMF);

		ListAdapter adapter = new HostArrayAdapter(this, hosts);

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent data = new Intent();
		data.putExtra("Host", hosts[position].toBundle());
		setResult(RESULT_OK, data);
		super.finish();
	}
}
