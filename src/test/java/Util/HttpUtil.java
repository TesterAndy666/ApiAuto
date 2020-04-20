package Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * post����
	 * 
	 * @param url       �����ַ
	 * @param paramsMap �������
	 * @param headers   ����ͷ
	 * @return
	 */
	public static String post(String url, Map<String, String> paramsMap, Map<String, String> headers) {
		String resultStr = "";
		// ����һ��post����
		HttpPost post = new HttpPost(url);
		// ����params�е�key
		Set<String> paramsKey = paramsMap.keySet();
		// ����������list
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		// ���ò���
		for (String paramsName : paramsKey) {
			parameters.add(new BasicNameValuePair(paramsName, paramsMap.get(paramsName)));
		}
		// ��������ͷ
		Set<String> headersKey = headers.keySet();
		for (String headkeysName : headersKey) {
			post.addHeader(headkeysName, headers.get(headkeysName));
		}
		resultStr = sendPackage(post);
		logger.info("��Ӧ" + resultStr);
		return resultStr;
	}

	/**
	 * post������
	 * 
	 * @param url �����ַ
	 * @return
	 */
	public static String postUnParam(String url, Map<String, String> headers) {
		String result = "";
		HttpPost post = new HttpPost(url);
		Set<String> headersKey = headers.keySet();
		for (String headkeysName : headersKey) {
			post.addHeader(headkeysName, headers.get(headkeysName));
		}
		try {
			result = sendPackage(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("��Ӧ" + result);
		return result;
	}

	/**
	 * post����������
	 * 
	 * @param url    �����ַ
	 * @param params �������
	 * @return
	 */
	public static String postByParams(String url, Map<String, String> paramsMap) {
		String resultStr = "";
		// ����һ��post����
		HttpPost post = new HttpPost(url);
		// ����params�е�key
		Set<String> keyset = paramsMap.keySet();
		// ����������list
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String name : keyset) {
			parameters.add(new BasicNameValuePair(name, paramsMap.get(name)));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(parameters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultStr = sendPackage(post);
		logger.info("��Ӧ" + resultStr);
		return resultStr;
	}

	/*
	 * public static String post(String url, Map<String, String> paramsMap) { String
	 * resultStr = ""; //����һ��post���� HttpPost post = new HttpPost(url);
	 * //����params�е�key Set<String> keyset = paramsMap.keySet(); //����������list
	 * List<NameValuePair> parameters = new ArrayList<NameValuePair>(); for (String
	 * name : keyset) { parameters.add(new BasicNameValuePair(name,
	 * paramsMap.get(name))); } try { //���������õ�post������ post.setEntity(new
	 * UrlEncodedFormEntity(parameters)); //���ɷ������� CloseableHttpClient httpClient =
	 * HttpClients.createDefault(); //�������õ���Ӧ CloseableHttpResponse response =
	 * httpClient.execute(post); //�����Ӧ�� HttpEntity responseEntity =
	 * response.getEntity(); resultStr = EntityUtils.toString(responseEntity);
	 * logger.info("��Ӧ" + resultStr); } catch (Exception e) { e.printStackTrace(); }
	 * return resultStr; }
	 */

	/**
	 * post������ͷ
	 * 
	 * @param url
	 * @param header
	 * @return
	 */
	public static String postByHeads(String url, Map<String, String> headers) {
		String result = "";
		HttpPost post = new HttpPost(url);
		// ��������ͷ
		Set<String> headersKey = headers.keySet();
		for (String headkeysName : headersKey) {
			post.addHeader(headkeysName, headers.get(headkeysName));
		}
		result = sendPackage(post);
		// JSONUtil.JsonAnalysis(result, "card", "individual_card_id");
		logger.info("��Ӧ" + result);
		return result;
	}

	/**
	 * get���δ�ͷ����
	 * 
	 * @param url
	 * @param paramsMap
	 * @param headers
	 * @return
	 */
	public static String get(String url, Map<String, String> paramsMap, Map<String, String> headers) {
		String resultStr = "";
		//���ò���
		Set<String> keysets = paramsMap.keySet();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String key : keysets) {
			parameters.add(new BasicNameValuePair(key, paramsMap.get(key)));
		}
		String paramStr = URLEncodedUtils.format(parameters, "utf-8");
		HttpGet get = new HttpGet(url + "?" + paramStr);
		//����ͷ����Ϣ
		Set<String> headersKey = headers.keySet();
		for (String headkeysName : headersKey) {
			get.addHeader(headkeysName, headers.get(headkeysName));
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity responseEntity = response.getEntity();
			resultStr = EntityUtils.toString(responseEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("��Ӧ" + resultStr);
		return resultStr;
	}

	public static String getUnParam(String url, Map<String, String> headers) {
		String resultStr = "";
		HttpGet get = new HttpGet(url);
		//����ͷ����Ϣ
		Set<String> headersKey = headers.keySet();
		for (String headkeysName : headersKey) {
			get.addHeader(headkeysName, headers.get(headkeysName));
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity responseEntity = response.getEntity();
			resultStr = EntityUtils.toString(responseEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("��Ӧ" + resultStr);
		return resultStr;
	}

	/**
	 * get��������
	 * 
	 * @param url       �����ַ
	 * @param paramsMap �������
	 * @return
	 */
	public static String getByParams(String url, Map<String, String> paramsMap) {
		String resultStr = "";
		Set<String> keysets = paramsMap.keySet();
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String key : keysets) {
			parameters.add(new BasicNameValuePair(key, paramsMap.get(key)));
		}
		String paramStr = URLEncodedUtils.format(parameters, "utf-8");
		HttpGet get = new HttpGet(url + "?" + paramStr);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity responseEntity = response.getEntity();
			resultStr = EntityUtils.toString(responseEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("��Ӧ" + resultStr);
		return resultStr;
	}

	/**
	 * get��ͷ����
	 * 
	 * @param url       �����ַ
	 * @param paramsMap �������
	 * @return
	 */
	public static String getByHeads(String url, Map<String, String> headers) {
		String resultStr = "";
		HttpGet get = new HttpGet();
		Set<String> headersKey = headers.keySet();
		for (String headkeysName : headersKey) {
			get.addHeader(headkeysName, headers.get(headkeysName));
		}
		resultStr = sendPackage(get);
		logger.info("��Ӧ" + resultStr);
		return resultStr;
	}

	/**
	 * ��������
	 * 
	 * @param url       �����ַ
	 * @param paramsMap �������
	 * @param type      ��������
	 * @return
	 */
	public static String request(String type, String url, Map<String, String> paramsMap, Map<String, String> headers) {
		String resultStr = "";
		if ("post".equalsIgnoreCase(type)) {
			if (headers.size() != 0) {
				if (paramsMap == null) {
					resultStr = postUnParam(url, headers);
				} else {
					resultStr = post(url, paramsMap, headers);
				}
			} else if (paramsMap.size() != 0) {
				resultStr = postByParams(url, paramsMap);
			}
		} else if ("get".equalsIgnoreCase(type)) {
			if (headers.size() != 0) {
				if (paramsMap == null) {
					resultStr = getUnParam(url, headers);
				} else {
					resultStr = get(url, paramsMap, headers);
				}
			} else if (paramsMap.size() != 0) {
				resultStr = getByParams(url, paramsMap);
			}
		}
		return resultStr;
	}

	/**
	 * post��������
	 * 
	 * @param post
	 * @return �������
	 */
	private static String sendPackage(HttpPost post) {
		String result = "";
		try {
			// ���ɷ�������
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// �������õ���Ӧ
			CloseableHttpResponse response = httpClient.execute(post);
			// �����Ӧ��
			HttpEntity responseEntity = response.getEntity();
			// ������Ӧ��
			result = EntityUtils.toString(responseEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * get��������
	 * 
	 * @param post
	 * @return �������
	 */
	private static String sendPackage(HttpGet get) {
		String result = "";
		try {
			// ���ɷ�������
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// �������õ���Ӧ
			CloseableHttpResponse response = httpClient.execute(get);
			// �����Ӧ��
			HttpEntity responseEntity = response.getEntity();
			// ������Ӧ��
			result = EntityUtils.toString(responseEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
