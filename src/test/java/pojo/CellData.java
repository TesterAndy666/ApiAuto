package pojo;

public class CellData {
	/**
	 * д��excel������pojo��
	 */
	private String caseId; //д�ص��к�

	private Integer cellNum; // д�ص��к�

	private String resultStr; //Ҫд�Ľ��

	public CellData(String caseId, Integer cellNum, String resultStr) {
		super();
		this.caseId = caseId;
		this.cellNum = cellNum;
		this.resultStr = resultStr;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Integer getCellNum() {
		return cellNum;
	}

	public void setCellNum(Integer cellNum) {
		this.cellNum = cellNum;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	@Override
	public String toString() {
		return "CellData [caseId=" + caseId + ", cellNum=" + cellNum + ", resultStr=" + resultStr + "]";
	}

}
