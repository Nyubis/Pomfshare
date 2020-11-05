package science.itaintrocket.pomfshare

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class HostListAdapter(
        private val context: Context,
        private val hosts: List<Host>
) : BaseAdapter() {
    override fun getCount(): Int {
        return hosts.size
    }

    override fun getItem(position: Int): Any {
        return hosts[position]
    }

    override fun getItemId(position: Int): Long {
        return -1
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val listItemView = view ?: inflater.inflate(R.layout.simple_list_item_2, parent, false)
        val lineOneView = listItemView.findViewById<View>(R.id.text1) as TextView
        val lineTwoView = listItemView.findViewById<View>(R.id.text2) as TextView
        val host = getItem(position) as Host
        lineOneView.text = host.name
        lineTwoView.text = host.description
        return listItemView
    }
}