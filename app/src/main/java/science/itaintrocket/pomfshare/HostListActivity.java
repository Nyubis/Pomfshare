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
		hosts = new Host[9];
		hosts[0] = new Host("Uguu", "https://uguu.se/api.php?d=upload-tool", "100MiB, 24 hours", Host.Type.UGUU);
		hosts[1] = new Host("Mixtape.moe", "https://mixtape.moe/upload.php?output=gyazo", "100MiB", Host.Type.POMF);
		hosts[2] = new Host("pomf.is", "https://pomf.is/upload.php?output=gyazo", "256MiB", Host.Type.POMF);
		hosts[3] = new Host("SICP", "http://sicp.me/", "25MiB", Host.Type.UGUU);
		hosts[4] = new Host("1339", "http://1339.cf/upload.php?output=gyazo", "100MiB", Host.Type.POMF);
		hosts[5] = new Host("Pomf.cat", "http://pomf.cat/upload.php?output=gyazo", "50MiB, Work in progress", Host.Type.POMF);
		hosts[6] = new Host("up.che.moe", "http://up.che.moe/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[7] = new Host("g.zxq.co", "http://g.zxq.co/upload.php?output=gyazo", "80MiB", Host.Type.POMF);
		hosts[8] = new Host("p.fuwafuwa.moe", "https://p.fuwafuwa.moe/upload.php?output=gyazo", "128MiB", Host.Type.POMF);

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
