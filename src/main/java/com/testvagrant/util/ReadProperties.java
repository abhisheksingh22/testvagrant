package com.testvagrant.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 
 * @author singhkabh
 *To read the Properties files and returns the property value.
 */

public class ReadProperties {

	private final Properties properties = new Properties();
	private File file;

	public void readFile(File file) throws Exception {
		setFile(file);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.file));
		this.properties.load(bis);
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 
	 * @param property property
	 * @return propertyValue
	 */
	public String getValue(String property) {
		return this.properties.getProperty(property);
	}
}
