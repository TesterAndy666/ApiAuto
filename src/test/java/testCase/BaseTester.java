package testCase;

import org.testng.annotations.AfterSuite;

import Util.ExcelUtil;

public class BaseTester {
    
	//�������ռ����������target/rest_info_result.xlsx�ĵ�һ��sheet����д���ռ�����
	@AfterSuite
	public void afterSuite(){
		ExcelUtil.batchWrite("/rest_info.xlsx","target/rest_info_result.xlsx",1);
	}

}
