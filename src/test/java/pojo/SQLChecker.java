package pojo;

import java.util.List;
import java.util.Map;

public class SQLChecker {
	/**
	 * sql�ı��
	 */
	private String no; 
	
	/**
	 * sql���
	 */
	private String sql;
	
	/**
	 * �������
	 */
	private List<Map<String, String>> expectedResult;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Map<String, String>> getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(List<Map<String, String>> expectedResult) {
		this.expectedResult = expectedResult;
	}

	@Override
	public String toString() {
		return "SQLChecker [no=" + no + ", sql=" + sql + ", expectedResult=" + expectedResult + "]";
	}

}
