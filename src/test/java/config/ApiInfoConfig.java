package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.ExcelUtil;
import Util.PropertiesUtil;
import pojo.ApiInfo;

public class ApiInfoConfig {

	private static List<ApiInfo> apiInfoList;

	private static Map<String, ApiInfo> apiInfoMap;
    
	//��ʼ������
	static {
		if (apiInfoList == null) {
			apiInfoList = new ArrayList<ApiInfo>();
		}
		
		if (apiInfoMap == null) {
			apiInfoMap = new HashMap<>();
		}
		
		String excelPath = getPath();
		int sheetNum = getSheetNum();
		
		//����Api����
		ExcelUtil.read(excelPath, sheetNum, ApiInfo.class);
		getApiInfoMap();
	}

	/**
	 * ��apiInfoListת����map���ݸ�ʽ����ΪapiId��ֵΪapiInfo����
	 */
	private static void getApiInfoMap() {
		for (ApiInfo apiInfo : apiInfoList) {
			String apiId = apiInfo.getApiId();
			apiInfoMap.put(apiId, apiInfo);
		}
	}

	/**
	 * ͨ��apiId��ȡtype	
	 * @param apiId
	 * @return
	 */
	public static String getTpyeByApiId(String apiId) {
		return apiInfoMap.get(apiId).getType();
	}

	/**
	 * ͨ��apiId��ȡurl
	 * @param apiId
	 * @return
	 */
	public static String getUrlByApiId(String apiId) {
		return apiInfoMap.get(apiId).getUrl();
	}

	/**
	 * ���apiInfoList����
	 * @return
	 */
	public static List<ApiInfo> getApiInfoList() {
		return apiInfoList;
	}

	/**
	 * ��apiInfoList���ApiInfo����
	 * @param apiInfo
	 */
	public static void add(ApiInfo apiInfo) {
		apiInfoList.add(apiInfo);
	}
	
	/**
	 *  �������л�ö�ȡexcel·��
	 * @return
	 */
	private static String getPath() {
		String path = (String) PropertiesUtil.properties.get("ApiInfoConfig.Path");
		return path;
	}
    
	/**
	 * �������л�ö�ȡexcel��sheetλ��
	 * @return
	 */
	private static int getSheetNum() {
		Object num = PropertiesUtil.properties.get("ApiInfoConfig.SheetNum");
		int number = Integer.parseInt(num.toString());
		return number;
	}
}
