package science.itaintrocket.pomfshare

class AuthManager {
    // TODO: actual persistence
    private val storage: HashMap<String, String> = hashMapOf()
    fun findAuthKey(host: Host): String? {
        return storage[host.name]
    }

    fun addAuthKey(host: Host, key: String) {
        storage[host.name!!] = key
    }
}