package science.itaintrocket.pomfshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HostArrayAdapter extends ArrayAdapter<Host> {
	public HostArrayAdapter(Context context, Host[] hosts) {
		super(context, android.R.layout.two_line_list_item, hosts);
	}

	@Override
	public android.view.View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View listItemView = convertView;
		if (null == convertView) {
			listItemView = inflater.inflate(android.R.layout.two_line_list_item, parent, false);
		}

		TextView lineOneView = (TextView)listItemView.findViewById(android.R.id.text1);
		TextView lineTwoView = (TextView)listItemView.findViewById(android.R.id.text2);

		Host host = (Host)getItem(position);
		lineOneView.setText(host.getName());
		lineTwoView.setText(host.getDescription());

		return listItemView;
	}
}
