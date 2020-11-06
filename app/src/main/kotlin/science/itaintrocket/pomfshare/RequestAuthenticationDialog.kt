package science.itaintrocket.pomfshare

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView


class RequestAuthenticationDialog : DialogFragment(), TextView.OnEditorActionListener {
    private lateinit var listener: RequestAuthenticationDialogListener
    private lateinit var dialogView: View
    private lateinit var passwordField: EditText

    interface RequestAuthenticationDialogListener {
        fun onDialogSubmit(dialog: DialogFragment, text: String?)
        fun onDialogCancel(dialog: DialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogView = inflater.inflate(R.layout.fragment_request_authentication, container)
        passwordField = dialogView.findViewById(R.id.password) as EditText
        dialog.setTitle("Authentication Required")
        dialog.setContentView(dialogView)
        passwordField.requestFocus();
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        passwordField.setOnEditorActionListener(this);
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as RequestAuthenticationDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException("$context must implement NoticeDialogListener")
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            val activity: RequestAuthenticationDialogListener =  activity as RequestAuthenticationDialogListener;
            activity.onDialogSubmit(this, passwordField.text.toString());
            this.dismiss();
            return true;
        }
        return false;
    }

}
