package org.fuchss.traylauncher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The entry point for the <em>Tray Launcher 4 Spring Boot</em>
 *
 * @author Dominik Fuchss
 */
public final class SpringBootTrayLauncher {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootTrayLauncher.class);

	private SpringBootTrayLauncher() {
		throw new IllegalAccessError();
	}

	/**
	 * Replace your appearance of
	 * {@code SpringApplication.run(Main.class, args)} with an invocation of
	 * this method.
	 *
	 * @param starterClass the starter class of the Spring Boot Application.
	 * @param args         the command line arguments
	 * @param conf         the configuration for the {@link SpringBootTrayLauncher}
	 */
	public static void run(Class<?> starterClass, String[] args, SpringBootTrayLauncherConfiguration conf) {
		final Container container = new Container();
		container.conf = conf;

		SpringApplicationBuilder builder = new SpringApplicationBuilder(starterClass);
		builder.headless(false);
		SpringApplication app = builder.application();

		// Add after startup listeners ..
		app.addListeners(e -> SpringBootTrayLauncher.afterStartup(e, () -> SpringBootTrayLauncher.initialMainLaunch(container)));
		app.addListeners(e -> SpringBootTrayLauncher.afterStartup(e, () -> SpringBootTrayLauncher.initTray(container)));

		container.ctx = app.run(args);
	}

	private static void openUrl(String urlToOpen) {
		if (urlToOpen == null) {
			return;
		}
		try {
			Desktop.getDesktop().browse(new URI(urlToOpen));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static void initialMainLaunch(Container container) {
		if (container.conf.isAutoOpenMainUrl()) {
			SpringBootTrayLauncher.openUrl(container.conf.getMainUrl());
		}
	}

	private static void initTray(Container container) {
		if (!SystemTray.isSupported()) {
			logger.error("Sorry, SystemTray is not supported");
			return;
		}

		SpringBootTrayLauncher.activateSystemLookAndFeel();

		var conf = container.conf;
		TrayIcon trayIcon = SpringBootTrayLauncher.createTrayIcon(container);
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
		exitItem.addActionListener(e -> SpringBootTrayLauncher.exit(container));
		popup.add(exitItem);

		try {
			SystemTray tray = SystemTray.getSystemTray();
			trayIcon.setPopupMenu(popup);
			tray.add(trayIcon);
		} catch (AWTException e) {
			logger.error("Sorry, TrayIcon could not be added.");
		}
	}

	private static TrayIcon createTrayIcon(Container container) {
		var conf = container.conf;

		TrayIcon trayIcon = new TrayIcon(conf.getTrayIconImage(), conf.getName());
		trayIcon.setImageAutoSize(true);

		container.trayIcon = trayIcon;
		return trayIcon;
	}

	private static void exit(Container container) {
		// Terminate Spring
		SpringApplication.exit(container.ctx);
		// Terminate AWT
		SystemTray tray = SystemTray.getSystemTray();
		tray.remove(container.trayIcon);
	}

	private static final class Container {
		SpringBootTrayLauncherConfiguration conf;
		ConfigurableApplicationContext ctx;
		TrayIcon trayIcon;
	}

	private static void activateSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void afterStartup(ApplicationEvent e, Runnable r) {
		if (!(e instanceof ApplicationStartedEvent)) {
			return;
		}
		r.run();
	}
}
