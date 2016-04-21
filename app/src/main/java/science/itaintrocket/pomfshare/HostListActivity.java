package science.itaintrocket.pomfshare;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;

public class HostListActivity extends ListActivity {
	private Host[] hosts;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		HostDbHelper helper = new HostDbHelper(getApplicationContext());
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] cols = {
				HostDbContract.HostTable._ID,
				HostDbContract.HostTable.COL_HOST_NAME,
				HostDbContract.HostTable.COL_HOST_URL,
				HostDbContract.HostTable.COL_HOST_DESC,
				HostDbContract.HostTable.COL_HOST_API,
		};
		Cursor c = db.query(HostDbContract.HostTable.TABLE_NAME, cols, null, null, null, null, null);
		ListAdapter adapter = new SimpleCursorAdapter(this,	android.R.layout.two_line_list_item, c,
				new String[]{ HostDbContract.HostTable.COL_HOST_NAME,
						      HostDbContract.HostTable.COL_HOST_DESC },
				new int[]{ android.R.id.text1, android.R.id.text2 },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		SQLiteCursor cur = (SQLiteCursor)getListView().getItemAtPosition(position);
		Intent data = new Intent();
		Host selected = new Host(cur.getString(1), cur.getString(2), cur.getString(3),
				Host.Type.valueOf(cur.getString(4)));

		data.putExtra("Host", selected.toBundle());
		setResult(RESULT_OK, data);
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.add_host:
				showAddHostDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void showAddHostDialog() {
		new AddHostDialogFragment().show(getFragmentManager(), "add_host");
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch(view.getId()) {
			case R.id.pomf_api:
				if (checked) {
					// Pirates are the best
					Log.d("ayy lmao", "pirates obv best");
				}
				break;
			case R.id.uguu_api:
				if (checked)
					// Ninjas rule
					break;
		}
	}
}
