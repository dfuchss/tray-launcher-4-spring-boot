# tray-launcher-4-spring-boot
[![Maven Deploy](https://github.com/dfuchss/tray-launcher-4-spring-boot/actions/workflows/deploy.yml/badge.svg)](https://github.com/dfuchss/tray-launcher-4-spring-boot/actions/workflows/deploy.yml)
[![Latest Release](https://img.shields.io/github/release/dfuchss/tray-launcher-4-spring-boot.svg)](https://github.com/dfuchss/tray-launcher-4-spring-boot/releases/latest)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dfuchss_tray-launcher-4-spring-boot&metric=alert_status)](https://sonarcloud.io/dashboard?id=dfuchss_tray-launcher-4-spring-boot)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?style=square)](https://github.com/dfuchss/tray-launcher-4-spring-boot/blob/master/LICENSE.md)

A simple tool to use a Tray Launcher for Spring Boot Applications

## HowTo
* Add the tray launcher dependency to maven ..
```xml
<dependencies>
    <dependency>
        <groupId>org.fuchss</groupId>
        <artifactId>tray-launcher-4-spring-boot</artifactId>
        <version>X.Y.Z</version>
    </dependency>
</dependencies>
```
* Create a simple configuration that contains at least the name, default url, and icon of your project:
```java
SpringBootTrayLauncherConfiguration conf = new SpringBootTrayLauncherConfiguration(
  "Name", 
  "http://localhost:8080", 
  Main.class.getResourceAsStream("/icon.jpg")
);
```
* Modify your main class:
```java
@SpringBootApplication
@EnableAutoConfiguration
public class Main {
	public static void main(final String[] args) {
		// Replace SpringApplication.run(Main.class, args) by this ..
		SpringBootTrayLauncherConfiguration conf = new SpringBootTrayLauncherConfiguration("Name", "http://localhost:8080", Main.class.getResourceAsStream("/icon.jpg"));
		// You may add additional Urls for the launcher
		conf.setAdditionalUrls(List.of(new URLEntry("Login", "http://localhost:8080/login")));
		SpringBootTrayLauncher.run(Main.class, args, conf);
	}
}
```
* Now you can start your application and use a simple tray icon :) 

![tray-example](.github/img/tray-example.png)
