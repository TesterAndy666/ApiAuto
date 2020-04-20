package config;

import java.util.ArrayList;
import java.util.List;

import Util.ExcelUtil;
import Util.PropertiesUtil;
import pojo.ApiParams;

public class ApiParamsConfig {

	private static List<ApiParams> apiParamsList;

	static {
		if (apiParamsList == null) {
			apiParamsList = new ArrayList<ApiParams>();
		}

		String path = getPath();
		int sheetNum = getSheetNum();
		//��������
		ExcelUtil.read(path, sheetNum, ApiParams.class);
	}

	public static void add(ApiParams apiParams) {
		apiParamsList.add(apiParams);
	}

	public static List<ApiParams> getApiparamsList() {
		return apiParamsList;
	}

	/**
	 *  ��ȡapi�����������
	 * @return  Object[][]
	 */
	public static Object[][] getParameters() {
		int size = apiParamsList.size();
		Object[][] datas = new Object[size][8];
		for (int i = 0; i < size; i++) {
			ApiParams apiParams = apiParamsList.get(i);
			datas[i][0] = apiParams.getCaseId();
			datas[i][1] = apiParams.getApiId();
			datas[i][2] = apiParams.getRequestData();
			datas[i][3] = apiParams.getExpectedResponseData();
			datas[i][4] = apiParams.getPreValidataSql();
			datas[i][5] = apiParams.getAfterValidataSql();
			datas[i][6] = apiParams.getIsHead();
			datas[i][7] = apiParams.getHeadData();
		}
		return datas;
	}
    
	/**
	 *  �������л�ö�ȡexcel·��
	 * @return
	 */
	private static String getPath() {
		String path = (String) PropertiesUtil.properties.get("ApiParamsConfig.Path");
		return path;
	}
    
	/**
	 * �������л�ö�ȡexcel��sheetλ��
	 * @return
	 */
	private static int getSheetNum() {
		Object num = PropertiesUtil.properties.get("ApiParamsConfig.SheetNum");
		int number = Integer.parseInt(num.toString());
		return number;
	}

}
