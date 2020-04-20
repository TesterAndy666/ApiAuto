package Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import pojo.CellData;
import pojo.CheckResult;
import pojo.SQLChecker;

public class SQLCheckUtil {

	/**
	 *   
	 * @param checkerStr Ҫ����sql����ַ���
	 * @return   ����Ľ����д�ص�excel�м�ȥ
	 */
	public static String getSQLCheckerResult(String checkerStr) {
		//ͨ������ת����һ���б�
		List<SQLChecker> cherkerList = JSONObject.parseArray(checkerStr, SQLChecker.class);
		//����һ������б���ÿ��sql�Ľ��
		List<CheckResult> checkResultList = new ArrayList<CheckResult>();
		for (SQLChecker sqlChecker : cherkerList) {
			String sql = sqlChecker.getSql();//�õ�������Ҫ������֤��sql
			List<Map<String, String>> actualResult = JDBCUtil.executeQuery(sql);
			String actualJson = JSONObject.toJSONString(actualResult);
			/*Map<String, Object> resultMap = records.get(0);
			String actualResult = "";
			Set<String> fields = resultMap.keySet();
			for (String field : fields) {
				actualResult = resultMap.get(field).toString();
			}*/
			//�����Ľ��
			List<Map<String, String>> expectedResult = sqlChecker.getExpectedResult();
			String expectedJson = JSONObject.toJSONString(expectedResult);
			//������Ե�ǰ����Ҫ����sql��һ����֤����Ķ���
			CheckResult checkResult = null;
			if (expectedJson.equals(actualJson)) {
				checkResult = new CheckResult(sqlChecker.getNo(), actualResult, "success");
			} else {
				checkResult = new CheckResult(sqlChecker.getNo(), actualResult, "failure");
			}
			//ÿ�μ������������ŵ��б���ȥ
			checkResultList.add(checkResult);
		}
		return JSONObject.toJSONString(checkResultList);
	}

	/**
	 * 
	 * @param caseId             caseId
	 * @param preValidateSql     sql���
	 * @param cellNum            д�ص��к�
	 */
	public static void dataValidata(String caseId, String ValidateSql, int cellNum) {
		// TODO Auto-generated method stub
		if (!StringUtil.isEmpty(ValidateSql)) {
			String ValidataSqlResult = SQLCheckUtil.getSQLCheckerResult(ValidateSql);
			ExcelUtil.resulToWriteList.add(new CellData(caseId, 10, ValidataSqlResult));
		}
	}
	
/*	public static void main(String[] args) {
		String checkerStr = "[{\"no\":\"1\",\"sql\":\"select * from apply where apply_id='123';\",\"expectedResult\":{\"totalNum\":\"0\"}}]";
		List<SQLChecker> cherkerList = JSONObject.parseArray(checkerStr, SQLChecker.class);
		for (SQLChecker sqlChecker : cherkerList) {
			System.out.println(sqlChecker.toString());
		}
	}*/
}
