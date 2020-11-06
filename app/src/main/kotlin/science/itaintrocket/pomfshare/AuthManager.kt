package science.itaintrocket.pomfshare

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

class AuthManager(
        private var settings: SharedPreferences
) {
    private val authKeyPrefix = "AUTH_KEY_"
    private val storage: HashMap<String, String> = hashMapOf()

    constructor(context: Context) : this(PreferenceManager.getDefaultSharedPreferences(context)) {
        Log.d("ayy lmao", "context: $context")
    }

    fun findAuthKey(host: Host): String? {
        val hostName = host.name ?: return null
        if (storage.containsKey(hostName)) {
            return storage[hostName]
        }
        val result = settings.getString(authKeyPrefix + hostName, null)
        result?.let { storage.put(hostName, it) }
        return result
    }

    fun addAuthKey(host: Host, authKey: String) {
        storage[host.name!!] = authKey
        val editor: SharedPreferences.Editor = settings.edit()
        editor.putString(authKeyPrefix + host.name, authKey)
        editor.apply()
    }
}