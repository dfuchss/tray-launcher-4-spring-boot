package org.fuchss.traylauncher;

/**
 * A simple url entry for the tray. It defines the name of the button in the
 * context menu of the tray icon, as well as the url to open.
 *
 * @author Dominik Fuchss
 */
public final class UrlEntry {
	private final String name;
	private final String url;

	/**
	 * Create by name of button and url.
	 *
	 * @param name the name of the button
	 * @param url  the url to open
	 */
	public UrlEntry(String name, String url) {
		this.name = name;
		this.url = url;
	}

	/**
	 * Get the name of the URL Button.
	 *
	 * @return the button's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the url to open.
	 *
	 * @return the url to open
	 */
	public String getUrl() {
		return this.url;
	}
}