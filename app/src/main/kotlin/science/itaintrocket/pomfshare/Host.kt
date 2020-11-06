package science.itaintrocket.pomfshare

import android.os.Bundle

class Host {
    enum class Type {
        UGUU, POMF
    }

    val name: String?
    val url: String?
    val description: String?
    val type: Type?
    val authRequired: Boolean?
    var authKey: String? = null

    constructor(name: String?, url: String?, description: String?, type: Type?, authRequired: Boolean = false,
                authKey: String? = null) {
        this.name = name
        this.url = url
        this.description = description
        this.type = type
        this.authRequired = authRequired
        this.authKey = authKey
    }

    constructor(bundle: Bundle) : this(
            bundle.getString(NAME_FIELD),
            bundle.getString(URL_FIELD),
            bundle.getString(DESC_FIELD),
            bundle.getSerializable(TYPE_FIELD) as Type?,
            bundle.getBoolean(AUTH_REQUIRED_FIELD, false),
            bundle.getString(AUTH_KEY)
    )

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(NAME_FIELD, name)
        bundle.putString(URL_FIELD, url)
        bundle.putString(DESC_FIELD, description)
        bundle.putSerializable(TYPE_FIELD, type)
        bundle.putBoolean(AUTH_REQUIRED_FIELD, authRequired?:false)
        bundle.putString(AUTH_KEY, authKey)
        return bundle
    }

    companion object {
        private const val NAME_FIELD = "name"
        private const val URL_FIELD = "url"
        private const val DESC_FIELD = "description"
        private const val TYPE_FIELD = "type"
        private const val AUTH_REQUIRED_FIELD = "authRequired"
        private const val AUTH_KEY = "authKey"
    }
}