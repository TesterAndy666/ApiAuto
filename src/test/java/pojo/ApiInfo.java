package pojo;

public class ApiInfo {
	/**
	 * �ӿ���Ϣpojo����
	 */
	private String apiId;   //�ӿ�id
	private String apiName; //�ӿ�����
	private String url;     //�ӿڵ�ַ
	private String type;    //����ӿ�����

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ApiInfo [apiId=" + apiId + ", apiName=" + apiName + ", url=" + url + ", type=" + type + "]";
	}

}
