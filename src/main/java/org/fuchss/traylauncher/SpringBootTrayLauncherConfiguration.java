package org.fuchss.traylauncher;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * The configuration for {@link SpringBootTrayLauncher}.
 *
 * @author Dominik Fuchss
 *
 */
public class SpringBootTrayLauncherConfiguration {
	private String name;
	private String mainUrl;
	private boolean autoOpenMainUrl = true;
	private Image trayIcon;
	private List<UrlEntry> entries;

	/**
	 * Set the name, main url (entry point to - see
	 * {@link #isAutoOpenMainUrl()}), and an image for the tray.
	 *
	 * @param name
	 *            the name of the application
	 * @param mainUrl
	 *            the main url
	 * @param trayIcon
	 *            the tray icon image as image
	 */
	public SpringBootTrayLauncherConfiguration(String name, String mainUrl, Image trayIcon) {
		this.setName(name);
		this.setMainUrl(mainUrl);
		this.setTrayIcon(trayIcon);
	}

	/**
	 * Set the name, main url (entry point to - see
	 * {@link #isAutoOpenMainUrl()}), and an image for the tray.
	 *
	 * @param name
	 *            the name of the application
	 * @param mainUrl
	 *            the main url
	 * @param trayIcon
	 *            the tray icon image as input stream
	 */
	public SpringBootTrayLauncherConfiguration(String name, String mainUrl, InputStream trayIcon) {
		this.setName(name);
		this.setMainUrl(mainUrl);
		this.setTrayIcon(trayIcon);
	}

	/**
	 * Get the name of the application.
	 *
	 * @return the name of the application
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the main url of the application. This url will be opened in a browser
	 * unless {@link #setAutoOpenMainUrl(boolean)} is set to {@code false}.
	 *
	 * @return the main url
	 */
	public String getMainUrl() {
		return this.mainUrl;
	}

	/**
	 * Get the image for the tray icon.
	 *
	 * @return the image for the tray icon
	 */
	public Image getTrayIcon() {
		return this.trayIcon;
	}

	/**
	 * Get additional url entries to be added to the tray icon context menu.
	 *
	 * @return additional urls for the context menu
	 */
	public List<UrlEntry> getAdditionalUrls() {
		return this.entries;
	}

	/**
	 * Indicates whether the application shall be opened in the browser upon
	 * start completed.
	 *
	 * @return an indicator for automatic opening of the application upon start
	 */
	public boolean isAutoOpenMainUrl() {
		return this.autoOpenMainUrl;
	}

	/**
	 * Setter for {@link #getName()}.
	 *
	 * @param name
	 *            the new name
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Setter for {@link #getMainUrl()}.
	 *
	 * @param mainUrl
	 *            the new main url
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
		return this;
	}

	/**
	 * Setter for {@link #getTrayIcon()}.
	 *
	 * @param trayIcon
	 *            the new tray icon image
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setTrayIcon(Image trayIcon) {
		this.trayIcon = trayIcon;
		return this;
	}

	/**
	 * Setter for {@link #getTrayIcon()}.
	 *
	 * @param trayIcon
	 *            the new tray icon image
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setTrayIcon(InputStream trayIcon) {
		try {
			this.trayIcon = ImageIO.read(trayIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Setter for {@link #getAdditionalUrls()}.
	 *
	 * @param entries
	 *            the new additional url entries
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setAdditionalUrls(List<UrlEntry> entries) {
		this.entries = entries;
		return this;
	}

	/**
	 * Setter for {@link #isAutoOpenMainUrl()}.
	 *
	 * @param autoOpenMainUrl
	 *            the new value for {@link #isAutoOpenMainUrl()}
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setAutoOpenMainUrl(boolean autoOpenMainUrl) {
		this.autoOpenMainUrl = autoOpenMainUrl;
		return this;
	}
}
