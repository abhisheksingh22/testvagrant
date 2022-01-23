package com.testvagrant.util;

import java.io.File;

public class SetProperties {

	// create reference of ReadProperties class.
	public static ReadProperties env;

	public SetProperties() {
		try {
			env = new ReadProperties();

			// Read env properties file
			env.readFile(new File("src/test/resources/env.properties"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
