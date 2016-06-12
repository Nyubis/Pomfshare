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
		hosts = new Host[22];
		hosts[0] = new Host("1339", "http://1339.cf/upload.php?output=gyazo", "100MiB", Host.Type.POMF);
		hosts[1] = new Host("cocaine.ninja", "https://cocaine.ninja/upload.php?output=gyazo", "128MiB", Host.Type.POMF);
		hosts[2] = new Host("comfy.moe", "https://comfy.moe/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[3] = new Host("cuntflaps.me", "https://cuntflaps.me/upload.php?output=gyazo", "250MiB", Host.Type.POMF);
		hosts[4] = new Host("desu.sh", "https://desu.sh/upload.php?output=gyazo", "2GiB", Host.Type.POMF);
		hosts[5] = new Host("filebunker.pw", "https://filebunker.pw/upload.php?output=gyazo", "100MiB", Host.Type.POMF);
		hosts[6] = new Host("glop.me", "http://glop.me/upload.php?output=gyazo", "10MiB", Host.Type.POMF);
		hosts[7] = new Host("g.zxq.co", "http://g.zxq.co/upload.php?output=gyazo", "80MiB", Host.Type.POMF);
		hosts[8] = new Host("kyaa.sg", "https://kyaa.sg/upload.php?output=gyazo", "100MiB", Host.Type.POMF);
		hosts[9] = new Host("Mixtape.moe", "https://mixtape.moe/upload.php?output=gyazo", "100MiB", Host.Type.POMF);
		hosts[10] = new Host("nigger.cat", "http://nigger.cat/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[11] = new Host("p.fuwafuwa.moe", "https://p.fuwafuwa.moe/upload.php?output=gyazo", "128MiB", Host.Type.POMF);
		hosts[12] = new Host("pomf.nyafuu.org", "http://pomf.nyafuu.org/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[13] = new Host("pomfe.co", "https://pomfe.co/upload.php?output=gyazo", "500MiB", Host.Type.POMF);
		hosts[14] = new Host("Pomf.cat", "http://pomf.cat/upload.php?output=gyazo", "50MiB, Work in progress", Host.Type.POMF);
		hosts[15] = new Host("pomf.is", "https://pomf.is/upload.php?output=gyazo", "256MiB", Host.Type.POMF);
		hosts[16] = new Host("qt.cx", "http://qt.cx/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[17] = new Host("reich.io", "http://reich.io/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[18] = new Host("SICP", "http://sicp.me/", "25MiB", Host.Type.UGUU);
		hosts[19] = new Host("steamy.moe", "https://steamy.moe/upload.php?output=gyazo", "512MiB", Host.Type.UGUU);
		hosts[19] = new Host("sugoi.vidyagam.es", "https://sugoi.vidyagam.es/upload.php?output=gyazo", "100MiB", Host.Type.UGUU);
		hosts[20] = new Host("Uguu", "https://uguu.se/api.php?d=upload-tool", "100MiB, 24 hours", Host.Type.UGUU);
		hosts[21] = new Host("up.asis.io", "http://up.asis.io/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[22] = new Host("up.che.moe", "http://up.che.moe/upload.php?output=gyazo", "50MiB", Host.Type.POMF);

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
