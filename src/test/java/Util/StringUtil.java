package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 1������null
	 * 2��"   "���ڿ�
	 * 3��""���ڿ�
	 * @param str  �ַ���
	 * @return
	 */
	public static boolean isEmpty(String str) {
		Boolean result = (str == null) || (str.replace("     ", "")).length() == 0;
		return result;
	}

	/**
	 *    �ֽ�����jsonKey��ֵ
	 * @param keyStr 
	 * @return
	 */
	public static List<Map<String, String>> analysisHopeStr(String keyStr) {
		List<Map<String, String>> resultList= new ArrayList<>();
		//������Ž������
		HashMap<String, String> resultMap = new HashMap<String, String>();
		//�������key��list����
		List<String> keyList = new ArrayList<String>();
		//�������value��list����
		List<String> valueList = new ArrayList<String>();
		// �ֽ�key��value
		String[] oneList = keyStr.split(",");
		for (int i = 0; i < oneList.length; i++) {
			if (i % 2 == 0) {
				keyList.add(oneList[i].toString());
			} else {
				valueList.add(oneList[i].toString());
			}
		}
		//���ֽ��ֵ����map����
		int forNum = oneList.length / 2;
		for (int j = 0; j < forNum; j++) {
			String key = keyList.get(j);
			String value = valueList.get(j);
			resultMap.put(key, value);
			resultList.add(resultMap);    		
		}
		return resultList;
	}
	
    /*
     * ��ʾ����������ַ�������Ϊmap����	
     */
	public static void main(String[] args) {
		String test = "data,0,banner,0";
		List<Map<String, String>> aHashMap = analysisHopeStr(test);
		int a=aHashMap.size();
		System.out.println(a);
	}
}
