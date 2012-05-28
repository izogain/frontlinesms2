package net.frontlinesms.install4j.custom;

import java.util.UUID;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;

class Util {
//> CONSTANTS
	static final String PROCESSING_URL = "http://register.frontlinesms.com/process/";
	private static final String URL_REGEX = "(((file|gopher|news|nntp|telnet|http|ftp|https|ftps|sftp)://)|(www\\.))+(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(/[a-zA-Z0-9\\&amp;%_\\./-~-]*)?";
	private static final String EMAIL_REGEX = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@(([0-9a-zA-Z])+([-\\w]*[0-9a-zA-Z])*\\.)+[a-zA-Z]{2,9})$";

//> PUBLIC UTILITY METHODS
	// TODO unit test
	public boolean validateUrl(String url) {
		boolean matches = url.matches(URL_REGEX);
		if(!matches) showAlert("Invalid Web Address \nTry e.g. www.google.com");
		return matches;
	}

	// TODO unit test
	public boolean validateEmailAddress(String email) {
		boolean matches = email.matches(EMAIL_REGEX);
		if(!matches) showAlert("Invalid Email \nTry e.g. jim@home.com");
		return matches;
	}

//> PACKAGE UTILITY METHODS
	static File getRegistrationPropertiesFile() {
		File frontlinesms2Directory = new File(System.getProperty("user.home"), ".frontlinesms2");
		return new File(frontlinesms2Directory, "registration.properties");
	}

	static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	static void log(String s) {
		System.out.println("\t: "+s);
	}

	static void createRegistrationPropertiesFile(String uuid, boolean registered) {
		File regPropFile = Util.getRegistrationPropertiesFile();
		try {
			if(!regPropFile.exists()) {
				regPropFile.createNewFile();
			}

			FileOutputStream fos = null;
			OutputStreamWriter osw = null;
			BufferedWriter out = null;
			try {
				fos = new FileOutputStream(regPropFile);
				osw = new OutputStreamWriter(fos, "UTF-8");
				out = new BufferedWriter(osw);
				createRegistrationPropertiesFile(out, uuid, registered);
			} finally {
				// Close all streams safely
				try { fos.close(); } catch(Exception _) { /* ignore */ }
				try { osw.close(); } catch(Exception _) { /* ignore */ }
				try { out.close(); } catch(Exception _) { /* ignore */ }
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// TODO unit test this method
	static void createRegistrationPropertiesFile(BufferedWriter out, String uuid, boolean registered) throws IOException {
		out.write("registered=" + registered + '\n');
		out.write("uuid=" + uuid + '\n');
	}

//> PRIVATE UTILITY METHODS
	// TODO move this to Alerter class so it can be mocked/spied in testing
	private void showAlert(String message) {
	    javax.swing.JOptionPane.showMessageDialog(null, message);
	}
}
