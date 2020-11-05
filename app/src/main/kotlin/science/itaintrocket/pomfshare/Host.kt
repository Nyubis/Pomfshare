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

    constructor(name: String?, url: String?, description: String?, type: Type?) {
        this.name = name
        this.url = url
        this.description = description
        this.type = type
    }

    constructor(bundle: Bundle) : this(
            bundle.getString(NAME_FIELD),
            bundle.getString(URL_FIELD),
            bundle.getString(DESC_FIELD),
            bundle.getSerializable(TYPE_FIELD) as Type?
    )

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(NAME_FIELD, name)
        bundle.putString(URL_FIELD, url)
        bundle.putString(DESC_FIELD, description)
        bundle.putSerializable(TYPE_FIELD, type)
        return bundle
    }

    companion object {
        private const val NAME_FIELD = "name"
        private const val URL_FIELD = "url"
        private const val DESC_FIELD = "description"
        private const val TYPE_FIELD = "type"
    }
}