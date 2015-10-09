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
		hosts = new Host[17];
		hosts[0] = new Host("Uguu", "http://uguu.se/api.php?d=upload-tool", "100MB, 24 hours", Host.Type.UGUU);
		hosts[1] = new Host("1339", "http://1339.cf/upload.php?output=gyazo", "100MB", Host.Type.POMF);
		hosts[2] = new Host("SICP", "http://sicp.me/", "25MB", Host.Type.UGUU);
		hosts[3] = new Host("Pomf.cat", "http://pomf.cat/upload.php?output=gyazo", "50MB, Work in progress", Host.Type.POMF);
		
		hosts[4] = new Host("up.che.moe", "http://up.che.moe/upload.php?output=gyazo", "http://cdn.che.moe", Host.Type.POMF);
        hosts[5] = new Host("bucket.pw", "https://bucket.pw/upload.php?output=gyazo", "https://dl.bucket.pw", Host.Type.POMF);
        hosts[6] = new Host("g.zxq.co", "http://g.zxq.co/upload.php?output=gyazo", "http://y.zxq.co", Host.Type.POMF);
        hosts[7] = new Host("kyaa.eu", "http://kyaa.eu/upload.php?output=gyazo", "https://r.kyaa.eu", Host.Type.POMF);
        hosts[8] = new Host("madokami.com", "https://madokami.com/upload", Host.Type.POMF);
        hosts[9] = new Host("matu.red", "http://matu.red/upload.php?output=gyazo", "http://x.matu.red", Host.Type.POMF);
        hosts[10] = new Host("maxfile.ro", "https://maxfile.ro/static/upload.php?output=gyazo", "https://d.maxfile.ro", Host.Type.POMF);
        hosts[11] = new Host("mixtape.moe", "https://mixtape.moe/upload.php?output=gyazo", Host.Type.POMF);
        hosts[12] = new Host("openhost.xyz", "http://openhost.xyz/upload.php?output=gyazo", Host.Type.POMF);
        hosts[13] = new Host("pantsu.cat", "https://pantsu.cat/upload.php?output=gyazo", Host.Type.POMF);
        hosts[14] = new Host("pomf.pl", "http://pomf.pl/upload.php?output=gyazo", "http://i.pomf.pl", Host.Type.POMF);
        hosts[15] = new Host("pomf.hummingbird.moe", "http://pomf.hummingbird.moe/upload.php?output=gyazo", "http://a.pomf.hummingbird.moe", Host.Type.POMF);
        hosts[16] = new Host("pomf.io", "http://pomf.io/upload.php?output=gyazo", Host.Type.POMF);
		
		
		
		
		

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
