package science.itaintrocket.pomfshare;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

import science.itaintrocket.pomfshare.Host;
import science.itaintrocket.pomfshare.HostArrayAdapter;

public class HostListActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Host[] hosts = new Host[3];
		hosts[0] = new Host("Uguu", "http://uguu.se/api.php?d=upload", "100MB, 24 hours", Host.Type.UGUU);
		hosts[1] = new Host("Pomf", "http://pomf.se/upload.php?output=gyazo", "50MB", Host.Type.POMF);
		hosts[2] = new Host("Some Pomf clone", "http://example.com/upload.php?output=gyazo", "┐(´∀｀)┌", Host.Type.POMF);

		ListAdapter adapter = new HostArrayAdapter(this, hosts);

		setListAdapter(adapter);
	}
}
