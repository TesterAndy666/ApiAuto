package testCase;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;

import Util.AssertResult;
import Util.ExcelUtil;
import Util.HeadUtil;
import Util.HttpUtil;
import Util.SQLCheckUtil;
import config.ApiInfoConfig;
import config.ApiParamsConfig;

public class ApiProcessor extends BaseTester {
	
	@Test(dataProvider = "getDatas")
	public void apiProcessor(String caseId, String apiId, String requestDataStr, String expectedResponseData,
			String preValidateSql, String afterValiDateSql, String isHead, String headData) {
		// 1��ǰ��sql��֤
		SQLCheckUtil.dataValidata(caseId, preValidateSql, 8);
		// 2������ӿڵ�����
		String type = ApiInfoConfig.getTpyeByApiId(apiId);
		// 3���ӿڵĵ�ַ
		String url = ApiInfoConfig.getUrlByApiId(apiId);
		// 4���������
		Map<String, String> params = (Map<String, String>) JSONObject.parse(requestDataStr);
		// ����ͷ
		Map<String, String> headMap = HeadUtil.handle(isHead, headData);
		// 5�������õ���Ӧ���
		String resultStr = HttpUtil.request(type, url, params, headMap);
		// ���Խ���Ƿ�ͨ��
		Assert.assertTrue(AssertResult.isPass(resultStr, expectedResponseData));
		// 6���ռ�ÿ��ִ�н������һ����д��excel
		ExcelUtil.writeResultToExcel(caseId, 6, resultStr);
		// 7��������֤
		SQLCheckUtil.dataValidata(caseId, afterValiDateSql, 10);
	}

	@DataProvider
	public Object[][] getDatas() {
		return ApiParamsConfig.getParameters();
	}

}
