package myassessment.lambdatestpages;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PropertiesFileRead {

	Properties properties;
	String applicationPropFilePath = "src//test//resources//application.properties";
	String capabiltiesPropFilePath = "src//test//resources//capabilities.properties";
	RemoteWebDriver driver;

	public String getCapPropertyFilePath() {
		return this.capabiltiesPropFilePath;
	}

	public String getAppPropertyFilePath() {
		return this.applicationPropFilePath;
	}

	public String getSeleniumAutomationPageUrl() {

		String seleniumautomationpage_url = getProperty(applicationPropFilePath, "seleniumautomationpage.url");

		if (seleniumautomationpage_url != null)
			return seleniumautomationpage_url;

		else
			throw new RuntimeException("SeleniumAutomationPage Url not specified in the Properties file.");

	}

	public String getGridUrl() {

		String grid_url = getProperty(applicationPropFilePath, "grid.url");

		if (grid_url != null)
			return grid_url;

		else
			throw new RuntimeException("Grid Url not specified in the Properties file.");

	}

	public RemoteWebDriver getRemoteWebDriver(String environment, String browserName, String browserVersion,
			String platformName) throws MalformedURLException {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (environment.equalsIgnoreCase("cloud")) {

			HashMap<String, String> capHashMap = getCapabilities(capabiltiesPropFilePath);

			capabilities.setCapability("browserName", browserName);
			capabilities.setCapability("browserVersion", browserVersion);
			capabilities.setCapability("platformName", platformName);
			capabilities.setCapability("user", capHashMap.get("user"));
			capabilities.setCapability("accessKey", capHashMap.get("accessKey"));
			capabilities.setCapability("build", capHashMap.get("build"));
			capabilities.setCapability("name", capHashMap.get("name"));
			capabilities.setCapability("selenium_version", capHashMap.get("selenium_version"));
			capabilities.setCapability("chrome.driver", capHashMap.get("chrome.driver"));
			capabilities.setCapability("firefox.driver", capHashMap.get("firefox.driver"));
			capabilities.setCapability("network", capHashMap.get("network"));
			capabilities.setCapability("visual", capHashMap.get("visual"));
			capabilities.setCapability("video", capHashMap.get("video"));
			capabilities.setCapability("console", capHashMap.get("console"));
			capabilities.setCapability("tunnel", capHashMap.get("tunnel"));
			capabilities.setCapability("tunnelName", capHashMap.get("tunnelName"));
			capabilities.setCapability("resolution", capHashMap.get("resolution"));

			try {
				System.out.println("https://" + capHashMap.get("user") + ":" + capHashMap.get("accessKey") + "@"
						+ "hub.lambdatest.com/wd/hub");
				driver = new RemoteWebDriver(new URL("https://" + capHashMap.get("user") + ":"
						+ capHashMap.get("accessKey") + "@" + "hub.lambdatest.com/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				System.out.println("Invalid grid URL");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		if (environment.equalsIgnoreCase("local")) {

			if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "chromedriver");
				ChromeOptions options = new ChromeOptions();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new RemoteWebDriver(new URL("http://192.168.0.14:4444/wd/hub"), capabilities);
			}
			if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "geckodriver");
				FirefoxOptions options = new FirefoxOptions();
				capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
				driver = new RemoteWebDriver(new URL("http://192.168.0.14:4444/wd/hub"), capabilities);
			}
		}
		return driver;
	}

	public String getProperty(String propertyFilePath, String propertyName) {

		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();

			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Properties file not found at " + propertyFilePath);

		}

		return properties.getProperty(propertyName);

	}

	public HashMap<String, String> getCapabilities(String cap_subpath) {

		HashMap<String, String> capHashMap = new HashMap<String, String>();

		if (cap_subpath != null) {

			capHashMap.put("user", getProperty(cap_subpath, "user"));
			capHashMap.put("accessKey", getProperty(cap_subpath, "accessKey"));
			capHashMap.put("build", getProperty(cap_subpath, "build"));
			capHashMap.put("name", getProperty(cap_subpath, "name"));
			capHashMap.put("selenium_version", getProperty(cap_subpath, "selenium_version"));
			capHashMap.put("chrome.driver", getProperty(cap_subpath, "chrome.driver"));
			capHashMap.put("firefox.driver", getProperty(cap_subpath, "firefox.driver"));
			capHashMap.put("console", getProperty(cap_subpath, "console"));
			capHashMap.put("network", getProperty(cap_subpath, "network"));
			capHashMap.put("visual", getProperty(cap_subpath, "visual"));
			capHashMap.put("video", getProperty(cap_subpath, "video"));
			capHashMap.put("tunnel", getProperty(cap_subpath, "tunnel"));
			capHashMap.put("tunnelName", getProperty(cap_subpath, "tunnelName"));
			capHashMap.put("resolution", getProperty(cap_subpath, "resolution"));

			return capHashMap;

		} else
			throw new RuntimeException("Capabilities not specified in the Capabilities file.");

	}

}
