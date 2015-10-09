package science.itaintrocket.pomfshare;

import android.os.Bundle;

public class Host {
	public enum Type { UGUU, POMF }

	private static final String NAME_FIELD = "name";
	private static final String URL_FIELD  = "url";
	private static final String DESC_FIELD = "description";
	private static final String TYPE_FIELD = "type";

	private String name;
	private String url;
	private String description;
	private Type type;

	public Host(String name, String url, String description, Type type) {
		this.name = name;
		this.url = url;
		this.description = description;
		this.type = type;
	}

	public Host(String name, String url, Type type){
		this.name = name;
		this.url = url;
		this.description = "No Description";
		this.type = type;
	}

	public Host(Bundle bundle) {
		this(
				bundle.getString(NAME_FIELD),
				bundle.getString(URL_FIELD),
				bundle.getString(DESC_FIELD),
				(Type) bundle.getSerializable(TYPE_FIELD)
		);
	}

	public Bundle toBundle() {
		Bundle bundle = new Bundle();
		bundle.putString(NAME_FIELD, name);
		bundle.putString(URL_FIELD, url);
		bundle.putString(DESC_FIELD, description);
		bundle.putSerializable(TYPE_FIELD, type);

		return bundle;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public Type getType() {
		return type;
	}

}

