package pojo;

import java.util.List;
import java.util.Map;

public class CheckResult {
	/**
	 * sql�ı��
	 */
	String no;
	
	/**
	 * ʵ�ʵĽ��
	 */
	List<Map<String, String>> actualResult;
	
	/**
	 * ��֤�Ƿ�ͨ����successΪͨ����failureΪʧ��
	 */
	String result;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<Map<String, String>> getActualResult() {
		return actualResult;
	}

	public void setActualResult(List<Map<String, String>> actualResult) {
		this.actualResult = actualResult;
	}

	@Override
	public String toString() {
		return "CheckResult [no=" + no + ", result=" + result + ", actualResult=" + actualResult + "]";
	}

	public CheckResult(String no, List<Map<String, String>> actualResult, String result) {
		super();
		this.no = no;
		this.result = result;
		this.actualResult = actualResult;
	}

}
