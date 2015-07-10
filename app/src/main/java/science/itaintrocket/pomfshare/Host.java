package science.itaintrocket.pomfshare;

public class Host {
	public enum Type { UGUU, POMF }

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

