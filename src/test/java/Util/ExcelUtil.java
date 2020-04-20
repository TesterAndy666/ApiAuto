package Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import config.ApiInfoConfig;
import config.ApiParamsConfig;
import pojo.ApiInfo;
import pojo.ApiParams;
import pojo.CellData;

public class ExcelUtil {
	/**
	 *       ��ȡexcel����
	 * @param excelPath   excel·��
	 * @param SheetIndex  sheet������0��ʼ��
	 * @param clazz       ת����Ӧ��pojo��
	 */
	public static void read(String excelPath, int SheetIndex, Class<?> clazz) {
		try {
			Workbook workbook = WorkbookFactory.create(ExcelUtil.class.getResourceAsStream(excelPath));
			//�õ���һ��sheet
			Sheet sheet = workbook.getSheetAt(SheetIndex);
			//�õ���һ��
			Row firstRow = sheet.getRow(0);
			int lastCellNum = firstRow.getLastCellNum();
			String[] fieldArray = new String[lastCellNum];
			for (int i = 0; i < lastCellNum; i++) {
				//�õ���ǰ������cell
				Cell cell = firstRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				//��cell����������Ϊstring
				cell.setCellType(CellType.STRING);
				//��ø���string���͵�ֵ
				String cellValue = cell.getStringCellValue();
				//ͨ���ش���ö�Ӧ������
				String fieldName = cellValue.substring(0, cellValue.indexOf("("));
				fieldArray[i] = fieldName;
			}
            
			//��ȡ����
			int lastRowNum = sheet.getLastRowNum();
			//����ÿһ��
			for (int i = 1; i <= lastRowNum; i++) {
				//��ȡ�ж���
				Row dataRow = sheet.getRow(i);
				//new��������ֽ������Ӧ�Ķ���洢����
				Object object = clazz.newInstance();
				//����ÿһ������У���ȡ������
				for (int j = 0; j < lastCellNum; j++) {
					Cell dataCell = dataRow.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					dataCell.setCellType(CellType.STRING);
					//��ȡ��ֵ
					String cellValue = dataCell.getStringCellValue();
					//��ȡ���ж�Ӧ���ֶ���
					String fieldName = fieldArray[j];
					//��ȡ���䷽����
					String setName = "set" + fieldName;
					Method setterMethod = clazz.getMethod(setName, String.class);
					//����object������������ֵ
					setterMethod.invoke(object, cellValue);
				}
				if (object instanceof ApiInfo) {
					ApiInfo apiInfo = (ApiInfo) object;
					ApiInfoConfig.add(apiInfo);
				} else if (object instanceof ApiParams) {
					ApiParams apiParams = (ApiParams) object;
					ApiParamsConfig.add(apiParams);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *      ���д��excel
	 * @param caseFilePath       ����·��(��·��)
	 * @param targetFilePath     д��Ŀ��·��(���·��)
	 * @param resultStr          д�ؽ��
	 * @param sheetIndex         д���ĵ�sheet����
	 * @param cellNum            д����һ��
	 * @param caseId             caseId
	 */
	public static void write(String caseFilePath, String targetFilePath, String resultStr, int sheetIndex, int cellNum,
			String caseId) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = ExcelUtil.class.getResourceAsStream(caseFilePath);
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 0; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String firstCellValue = cell.getStringCellValue();
				if (firstCellValue.equals(caseId)) {
					Cell cellToBeWrite = row.getCell(cellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToBeWrite.setCellType(CellType.STRING);
					cellToBeWrite.setCellValue(resultStr);
					break;
				}
			}
			os = new FileOutputStream(new File(targetFilePath));
			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����д��,ͨ��map����ʽ
	 *   ��������Map<String,Map<String,String>>
	 * @param caseFilePath      Դ�ļ�   
	 * @param targetFilePath    Ŀ���ļ�
	 * @param sheetIndex        sheet������
	 *  
	 * 1���ҵ�ĳ�У�ĳ���п����кܶ�����Ҫ����
	 *   ���磺��һ�У������в���aaa����5�в���bbb
	 *       �ڶ��У��ڶ��в���ddd����5�в���fff
	 */

	public static Map<String, Map<Integer, String>> resulToWriteMap = new HashMap<String, Map<Integer, String>>();

	public static List<CellData> resulToWriteList = new ArrayList<CellData>();

	/**
	 * pojo����ͨ��List�ķ�ʽ
	 * @param caseFilePath
	 * @param targetFilePath
	 * @param sheetIndex
	 */
	public static void batchWrite(String caseFilePath, String targetFilePath, int sheetIndex) {

		InputStream is = null;
		OutputStream os = null;

		try {
			is = ExcelUtil.class.getResourceAsStream(caseFilePath);
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			int lastRowNum = sheet.getLastRowNum();
			for (CellData cellData : resulToWriteList) {
				for (int i = 0; i <= lastRowNum; i++) {
					//�õ���ǰ��������һ��
					Row row = sheet.getRow(i);
					//�õ���һ��
					Cell cell = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					//�õ���һ�е�ֵ
					String firstCellValue = cell.getStringCellValue();
					//�Ƚϵ�ǰ�еĵ�һ�е�ֵ�Ƿ���ڴ����caseId
					if (firstCellValue.equals(cellData.getCaseId())) {
						Cell cellToBeWrite = row.getCell(cellData.getCellNum() - 1,
								MissingCellPolicy.CREATE_NULL_AS_BLANK);
						cellToBeWrite.setCellType(CellType.STRING);
						cellToBeWrite.setCellValue(cellData.getResultStr());
						break;
					}
				}
			}
			//��һ�����ص�excel�ļ������ݼ��ص�java��������������������֮ǰ���Ǽ����ĵ���������Ϣ�Լ������Լ��޸ĵ���Ϣ
			os = new FileOutputStream(new File(targetFilePath));
			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeResultToExcel(String caseId, int celltarget, String resultStr) {
		resulToWriteList.add(new CellData(caseId, celltarget, resultStr));
	}

	//	public static void main(String[] args) {
	//		read("/rest_info1.xlsx", 1, ApiParams.class);
	//		
	//	}
}
