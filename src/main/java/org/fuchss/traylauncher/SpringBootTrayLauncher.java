package org.fuchss.traylauncher;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The entry point for the <em>Tray Launcher 4 Spring Boot</em>
 *
 * @author Dominik Fuchss
 *
 */
public final class SpringBootTrayLauncher {
	private SpringBootTrayLauncher() {
		throw new IllegalAccessError();
	}

	/**
	 * Replace your appearance of
	 * {@code SpringApplication.run(Main.class, args)} with an invocation of
	 * this method.
	 *
	 * @param starterClass
	 *            the starter class of the Spring Boot Application.
	 * @param args
	 *            the command line arguments
	 * @param conf
	 *            the configuration for the {@link SpringBootTrayLauncher}
	 */
	public static void run(Class<?> starterClass, String[] args, SpringBootTrayLauncherConfiguration conf) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(starterClass);
		builder.headless(false);
		SpringApplication app = builder.application();
		ConfigurableApplicationContext ctx = app.run(args);

		if (conf.isAutoOpenMainUrl()) {
			SpringBootTrayLauncher.openUrl(conf.getMainUrl());
		}

		SpringBootTrayLauncher.initTray(conf, ctx);
	}

	private static void initTray(SpringBootTrayLauncherConfiguration conf, ConfigurableApplicationContext ctx) {
		if (!SystemTray.isSupported()) {
			System.err.println("Sorry, SystemTray is not supported");
			return;
		}
		TrayIcon trayIcon = SpringBootTrayLauncher.createTrayIcon(conf);
		trayIcon.addActionListener(e -> SpringBootTrayLauncher.openUrl(conf.getMainUrl()));

		PopupMenu popup = new PopupMenu();

		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(e -> SpringBootTrayLauncher.openUrl(conf.getMainUrl()));
		popup.add(openItem);

		if (conf.getAdditionalUrls() != null) {
			for (UrlEntry entry : conf.getAdditionalUrls()) {
				MenuItem entryItem = new MenuItem(entry.getName());
				entryItem.addActionListener(e -> SpringBootTrayLauncher.openUrl(entry.getUrl()));
				popup.add(entryItem);
			}
		}

		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(e -> SpringBootTrayLauncher.exit(ctx, trayIcon));
		popup.add(exitItem);

		try {
			SystemTray tray = SystemTray.getSystemTray();
			trayIcon.setPopupMenu(popup);
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println("Sorry, TrayIcon could not be added.");
		}
	}

	private static TrayIcon createTrayIcon(SpringBootTrayLauncherConfiguration conf) {
		TrayIcon icon = new TrayIcon(conf.getTrayIcon(), conf.getName());
		icon.setImageAutoSize(true);
		return icon;
	}

	private static void exit(ConfigurableApplicationContext ctx, TrayIcon trayIcon) {
		// Terminate Spring
		SpringApplication.exit(ctx);
		// Terminate AWT
		SystemTray tray = SystemTray.getSystemTray();
		tray.remove(trayIcon);
	}

	private static void openUrl(String urlToOpen) {
		if (urlToOpen == null) {
			return;
		}
		try {
			System.out.println("Opening .. " + urlToOpen);
			Desktop.getDesktop().browse(new URI(urlToOpen));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
