package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties properties;

	static {
		if (properties == null) {
			properties = new Properties();
		}
		properties = getProperties("src/test/resources/path.properties");
//		String path = (String) PropertiesUtil.properties.get("ApiParamsConfig.Path");
//		int num = (int) PropertiesUtil.properties.get("ApiParamsConfig.SheetNum");
	}

	/**
	 *  ����properties�ļ�����ȡkey��value
	 * @param path  properties�ļ�·�������磺src/test/resources/test.properties
	 * @param key   
	 * @return
	 */
	public static Properties getProperties(String path) {
		Properties properties = new Properties();
		File file = new File(path);
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
	

}
