package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

	/**
	 *  ��ȡjson��ָ��key��ֵ
	 * @param key  
	 * @param json
	 * @return
	 */
	public static String getKeyVal(String key, String json) {
		String resultStr = "";
		JSONObject jsonObject = JSONObject.parseObject(json);
		if (jsonObject.containsKey(key)) {
			resultStr = jsonObject.getString(key);
		} else {
			resultStr = getJsonVal(key, jsonObject);
		}
		return resultStr;
	}

	/**
	 *   ���json��key��ֵ
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	private static String getJsonVal(String key, JSONObject jsonObject) {
		//����һ���������
		String resultStr = "";
		//��jsonObjectת����һ��map����
		Map<String, Object> map = jsonObject.getInnerMap();
		//���map�ļ�����
		Set<String> mapKey = map.keySet();
		//����Set
		for (String keyStr : mapKey) {
			//���map��ֵ
			String value = map.get(keyStr).toString();
			//���ֵ�а���{�ͣ��������ֵ��һ��ʵ�������ֵ�а���[��]�������ֵ��һ������������������
			if (keyStr.equals(key)) {
				resultStr = value;
				break;
			} else if (value.substring(0, 1).equals("[")) {
				JSONArray jsonArray = JSONObject.parseArray(value);
				for (Object object : jsonArray) {
					JSONObject innerObject = JSONObject.parseObject(object.toString());
					if (innerObject.containsKey(key)) {
						resultStr = innerObject.getString(key);
					}

				}
			} else if (value.substring(0, 1).equals("{")) {
				JSONObject innerObject = JSONObject.parseObject(value);
				if (innerObject.containsKey(key)) {
					resultStr = innerObject.getString(key);
				} else {
					resultStr = getJsonVal(key, innerObject);
				}
			}
		}
		return resultStr;
	}

	/**
	 * ����
	 * @param args
	 */
	public static void main(String[] args) {
		String key = "message";
		String json = "{\"code\":0,\"message\":\"�ɹ�\",\"data\":{\"studetn\":[{\"name\":\"xiaoming\",\"age\":3,\"sex\":\"man\"},{\"name\":\"xiaohong\",\"age\":3,\"sex\":\"man\"},{\"name\":\"xiaolan\",\"age\":3,\"sex\":\"man\",\"scoce\":[{\"money\":20000,\"deadline\":\"3��\",\"title\":\"�����Ƽ�\"},{\"money\":8000,\"deadline\":\"30��\",\"title\":\"�����ת\"},{\"money\":8000,\"deadline\":\"30��\",\"title\":\"���ٷſ�\"}]},{\"name\":\"xiaolv\",\"age\":3,\"sex\":\"man\"}],\"teacher\":[{\"name\":\"xiaoming\",\"age\":18,\"sex\":\"man\"},{\"name\":\"xiaohong\",\"age\":18,\"sex\":\"man\"},{\"name\":\"xiaolan\",\"age\":18,\"sex\":\"man\"},{\"name\":\"xiaolv\",\"age\":18,\"sex\":\"man\"}],\"is_reloan\":false,\"is_new\":true,\"product_code\":\"4000_3_30_distribute\",\"product_id\":670,\"redirect_url\":\"http://paydayloan.samlin.dsqtest.kuainiujinke.com/m/#{/process\"}}";
		String aString = getKeyVal(key, json);
		System.out.println(aString);

	}

	public static void jsonUtil(String key, String json) {
		JSONObject jsonObject = JSON.parseObject(json);
		if (jsonObject.containsKey(key)) {
			System.out.println(jsonObject.getString(key));
		} else {
			// isInnerJson(key, json);
		}
	}

	private static List<String> getOneKeyAllValue(String key, String json) {
		//����һ���������key��ֵ
		List<String> keyValueList = new ArrayList<String>();
		//��json�ַ���ת��ΪJSONObject����
		JSONObject jsonObject = JSON.parseObject(json);
		//תΪmap������ʽ
		HashMap<String, Object> map = (HashMap<String, Object>) jsonObject.getInnerMap();
		//��ȡmap��key����
		Set<String> mapKey = map.keySet();
		//����key
		for (String keyString : mapKey) {
			if (keyString.equals(key)) {
				keyValueList.add((String) map.get(keyString));
			}
			//������һ��map�����key��ֵ
			String value = map.get(keyString).toString();
			//System.out.println(value);
			//���ֵ�м仹��jsons���飬��������
			if (value.substring(0).equals("[")) {
				System.out.println(value);
				JSONArray jsonArray = JSON.parseArray(value);
				for (Object object : jsonArray) {
					System.out.println(object.toString());
					JSONObject InnerjsonObject = JSON.parseObject(object.toString());
					//תΪmap������ʽ
					HashMap<String, Object> InnerMap = (HashMap<String, Object>) InnerjsonObject.getInnerMap();
					//��ȡmap��key����
					Set<String> InnerMapKey = map.keySet();
					//����key
					for (String InnerKey : InnerMapKey) {
						if (InnerKey.equals(key)) {
							keyValueList.add((String) map.get(InnerKey));
						}
					}
				}
			} else if (value.substring(0).equals("{")) { //���ֵ��һ��json�ַ���
				System.out.println(value);
				JSONObject InnerjsonObject = JSON.parseObject(value);
				if (InnerjsonObject.containsKey(key)) {
					System.out.println(InnerjsonObject.getString(key));
				} else {
					getOneKeyAllValue(key, value);
				}
			}
		}
		return keyValueList;
	}

	private static List<String> getOneKeyAllValue2(String key, String json) {
		//����һ���������key��ֵ
		List<String> keyValueList = new ArrayList<String>();
		//��json�ַ���ת��ΪJSONObject����
		JSONObject jsonObject = JSON.parseObject(json);
		//תΪmap������ʽ
		HashMap<String, Object> map = (HashMap<String, Object>) jsonObject.getInnerMap();
		if (jsonObject.containsKey(key)) {
			keyValueList.add((String) map.get(key));
		}
		//��ȡmap��key����
		Set<String> mapKey = map.keySet();
		//����key
		for (String keyString : mapKey) {
			//������һ��map�����key��ֵ
			String value = map.get(keyString).toString();
			//System.out.println(value);
			//���ֵ�м仹��jsons���飬��������
			if (value.substring(0, 1).equals("[")) {
				System.out.println(value);
				JSONArray jsonArray = JSON.parseArray(value);
				for (Object object : jsonArray) {
					System.out.println(object.toString());
					JSONObject InnerjsonObject = JSON.parseObject(object.toString());
					if (InnerjsonObject.containsKey(key)) {
						keyValueList.add((String) map.get(key));
					} else {
						// TODO ���������⣬��Ҫ���
						getOneKeyAllValue(key, value);
					}
				}
			} else if (value.substring(0, 1).equals("{")) { //���ֵ��һ��json�ַ���
				System.out.println(value);
				JSONObject InnerjsonObject = JSON.parseObject(value);
				if (InnerjsonObject.containsKey(key)) {
					System.out.println(InnerjsonObject.getString(key));
					keyValueList.add((String) map.get(key));
				} else {
					// TODO ���������⣬��Ҫ���
					getOneKeyAllValue(key, value);
				}
			}
		}
		return keyValueList;
	}

	/*public static void main(String[] args) {
		String key = "image_url";
		String json = "{\"code\":0,\"message\":\"�ɹ�\",\"data\":{\"is_login\":false,\"is_new_user\":false,\"banner\":[{\"image_url\":\"https://cashtest-1251122539.cossh.myqcloud.com/app/20190410182040976123336.png\",\"redirect_url\":\"http://paydayloan.xmw.dsqtest.kuainiujinke.com/active/oldUserInvite/#/invite\",\"title\":\"��ҳbanner������\"}],\"wechat_account\":{\"account\":\"DSQ�Ƽ�\",\"qrcode_url\":\"https://paydayloanv4-1251122539.cossh.myqcloud.com/app/2018122513073383888923.jpg\",\"msg\":\"��ע΢�Ź��ںš�DSQ�Ƽ���\\n����Ϣ���Ż�ȯ���ڷ����У�\",\"save_qrcode_header\":\"����ͼƬ�ɹ�\",\"save_qrcode_text\":\"��΢��-ɨһɨ\\n�����ѡȡ��ά��-��ע���ں�\",\"copy_account_header\":\"���ƹ��ںųɹ�\",\"copy_account_text\":\"����΢��-�������-���ں�ճ����\\n����-��ע΢�źţ����ཱƷ��������\"},\"config\":{\"tip\":\"����ѧ���ṩ����\"},\"multi\":{\"max_money\":20000,\"borrowed_money\":0,\"click_url\":\"http://paydayloan.samlin.dsqtest.kuainiujinke.com/m/#/auth\",\"click_limit\":0,\"apply_code\":\"\",\"apply_status\":0,\"loan_channel\":0,\"onclick_tip\":\"��Ҫ���\",\"button_color\":\"#fff\",\"is_loan_market\":true,\"show_info\":{\"show_text\":\"\",\"icon_url\":\"\"},\"repay_date\":\"\",\"apply_count\":0,\"extra_tip_info\":{},\"home_test\":\"no\",\"home_test_url\":\"\",\"is_queuing\":false},\"pre_loan\":{\"can_loan\":false},\"reloan\":{\"can_loan\":false},\"hide_banner\":false,\"new_user_button\":[{\"money\":20000,\"deadline\":\"3��\",\"title\":\"�����Ƽ�\"},{\"money\":8000,\"deadline\":\"30��\",\"title\":\"�����ת\"},{\"money\":8000,\"deadline\":\"30��\",\"title\":\"���ٷſ�\"}],\"is_old_pull_new\":true,\"is_refuse\":false,\"refuse_end_time\":\"\",\"banner_diversion\":{},\"service\":{},\"is_overdue\":false,\"is_order\":false,\"is_overdue_0\":false,\"is_overdue_7\":false,\"scrollInfo\":[{\"info\":\"����������1����ǰ���ڽ��14000Ԫ\"},{\"info\":\"�ຣ֣С��1����ǰ���ڽ��13000Ԫ\"},{\"info\":\"����������1����ǰ���ڽ��16000Ԫ\"},{\"info\":\"������С��1����ǰ�ɹ����6000Ԫ\"},{\"info\":\"����������1����ǰ���ڽ��14000Ԫ\"},{\"info\":\"����������1����ǰ���ڽ��19000Ԫ\"},{\"info\":\"������С��1����ǰ�ɹ����9000Ԫ\"},{\"info\":\"�ຣ��С��1����ǰ�ɹ����8000Ԫ\"},{\"info\":\"������С��1����ǰ�ɹ����7000Ԫ\"},{\"info\":\"������С��1����ǰ�ɹ����9000Ԫ\"},{\"info\":\"ɽ��������1����ǰ���ڽ��16000Ԫ\"},{\"info\":\"�Ĵ���С��1����ǰ�ɹ����6000Ԫ\"},{\"info\":\"����������1����ǰ���ڽ��10000Ԫ\"},{\"info\":\"�Ϻ���С��1����ǰ���ڽ��19000Ԫ\"},{\"info\":\"ɽ��������1����ǰ�ɹ����6000Ԫ\"},{\"info\":\"����������1����ǰ���ڽ��12000Ԫ\"},{\"info\":\"������С��1����ǰ�ɹ����10000Ԫ\"},{\"info\":\"����������1����ǰ���ڽ��19000Ԫ\"},{\"info\":\"������С��1����ǰ���ڽ��20000Ԫ\"},{\"info\":\"�ຣ��С��1����ǰ���ڽ��14000Ԫ\"}],\"advanced_is_show\":0,\"next_allow_apply_time\":null}}";
				List<String> values = getOneKeyAllValue2(key, json);
				for (String string : values) {
					System.out.println(string);
				}*/

}
