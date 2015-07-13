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

		hosts = new Host[3];
		hosts[0] = new Host("Uguu", "http://uguu.se/api.php?d=upload", "100MB, 24 hours", Host.Type.UGUU);
		hosts[1] = new Host("Pomf", "http://pomf.se/upload.php?output=gyazo", "50MB", Host.Type.POMF);
		hosts[2] = new Host("Some Pomf clone", "http://example.com/upload.php?output=gyazo", "┐(´∀｀)┌", Host.Type.POMF);

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
