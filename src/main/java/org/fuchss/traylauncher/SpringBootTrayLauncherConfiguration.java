package org.fuchss.traylauncher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The configuration for {@link SpringBootTrayLauncher}.
 *
 * @author Dominik Fuchss
 */
public class SpringBootTrayLauncherConfiguration {
	private String name;
	private String mainUrl;
	private boolean autoOpenMainUrl = true;
	private Image trayIconImage;
	private List<UrlEntry> additionalEntries;

	/**
	 * Set the name, main url (entry point to - see
	 * {@link #isAutoOpenMainUrl()}), and an image for the tray.
	 *
	 * @param name     the name of the application
	 * @param mainUrl  the main url
	 * @param trayIcon the tray icon image as image
	 */
	public SpringBootTrayLauncherConfiguration(String name, String mainUrl, Image trayIcon) {
		this.setName(name);
		this.setMainUrl(mainUrl);
		this.setTrayIconImage(trayIcon);
	}

	/**
	 * Set the name, main url (entry point to - see
	 * {@link #isAutoOpenMainUrl()}), and an image for the tray.
	 *
	 * @param name     the name of the application
	 * @param mainUrl  the main url
	 * @param trayIcon the tray icon image as input stream
	 */
	public SpringBootTrayLauncherConfiguration(String name, String mainUrl, InputStream trayIcon) {
		this.setName(name);
		this.setMainUrl(mainUrl);
		this.setTrayIconImage(trayIcon);
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
	public Image getTrayIconImage() {
		return this.trayIconImage;
	}

	/**
	 * Get additional url entries to be added to the tray icon context menu.
	 *
	 * @return additional urls for the context menu
	 */
	public List<UrlEntry> getAdditionalUrls() {
		return this.additionalEntries;
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
	 * @param name the new name
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Setter for {@link #getMainUrl()}.
	 *
	 * @param mainUrl the new main url
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
		return this;
	}

	/**
	 * Setter for {@link #getTrayIconImage()}.
	 *
	 * @param trayIcon the new tray icon image
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setTrayIconImage(Image trayIcon) {
		this.trayIconImage = trayIcon;
		return this;
	}

	/**
	 * Setter for {@link #getTrayIconImage()}.
	 *
	 * @param trayIcon the new tray icon image
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setTrayIconImage(InputStream trayIcon) {
		try {
			this.trayIconImage = ImageIO.read(trayIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Setter for {@link #getAdditionalUrls()}.
	 *
	 * @param entries the new additional url entries
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setAdditionalUrls(List<UrlEntry> entries) {
		this.additionalEntries = entries;
		return this;
	}

	/**
	 * Setter for {@link #isAutoOpenMainUrl()}.
	 *
	 * @param autoOpenMainUrl the new value for {@link #isAutoOpenMainUrl()}
	 * @return {@code this}
	 */
	public SpringBootTrayLauncherConfiguration setAutoOpenMainUrl(boolean autoOpenMainUrl) {
		this.autoOpenMainUrl = autoOpenMainUrl;
		return this;
	}
}
