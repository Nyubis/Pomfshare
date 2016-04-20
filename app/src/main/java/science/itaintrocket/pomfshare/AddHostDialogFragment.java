package science.itaintrocket.pomfshare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

public class AddHostDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.d("ayy lmao", "test?");
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setMessage("If you fill this out wrong the app will probably crash")
				.setTitle("Add Host")
				.setView(inflater.inflate(R.layout.dialog_add_host, null))
				.setPositiveButton("Add", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Add the host
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// This entire method can maybe be removed?
					}
				});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}