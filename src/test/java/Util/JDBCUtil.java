package Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JDBCUtil {

	private static String url;
	private static String user;
	private static String password;

	static {
		Properties properties = new Properties();
		try {
			properties.load(JDBCUtil.class.getResourceAsStream("/jdbc.properties"));
			url = properties.getProperty("jdbc.url");
			user = properties.getProperty("jdbc.user");
			password = properties.getProperty("jdbc.password");

			// ��������
			try {
				Class.forName(properties.getProperty("jdbc.driver"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("�ļ����س����쳣");
			e.printStackTrace();
		}

	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;

	}

	public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void close(PreparedStatement preparedStatement, Connection connection) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * ���ݿ������ɾ����
	 * 
	 * @param sql
	 *            ������sql
	 * @param params
	 *            �����б�
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void zeng_shan_gai(String sql, Object... params) throws ClassNotFoundException, SQLException {

		// �������
		Connection conn = getConnection();
		// sqlԤ����
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// �����ĸ����ǲ�ȷ���ģ�����Ҫ�ÿɱ����
		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
		pstmt.setString(1, "happy");
		pstmt.setString(2, "qweqw");
		pstmt.setString(3, "13333333333");
		// ִ��sql
		pstmt.execute();
		// �ر���Դ
		close(pstmt, conn);
	}

	/**
	 * ��ѯ
	 * 
	 * @param sql
	 *            ִ�е�sql
	 * @param params
	 *            ռλ�Ĳ���
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static List<Map<String, String>> executeQuery(String sql, Object... params) {
		List<Map<String, String>> recordList = null;
		// ��������
		Connection conn = getConnection();
		// ׼����������(Ԥ����)
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			// ѭ������ռλ����ֵ
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			ResultSet resultSet = pstmt.executeQuery();
			// ���Ԫ����:ͨ�����Ԫ���ݶ��󣬿����õ�������е�һЩ��Ϣ������ÿ���ֶε����ͣ����ƣ�����
			ResultSetMetaData metaData = resultSet.getMetaData();
			// ��ö����У��еĸ�����
			int numberOfColumns = metaData.getColumnCount();
			// ������ѯ���
			recordList = new ArrayList<Map<String, String>>();
			while (resultSet.next()) {
				Map<String, String> recordMap = new HashMap<String, String>();
				for (int i = 0; i < numberOfColumns; i++) {
					// ���ĳ�е�����
					String columnName = metaData.getColumnName(i + 1);
					// ����е�ֵ
					String columnValue =  resultSet.getString(i + 1);
					// �Ѹ������ݵĵ�ǰ�е���Ϣ������Map������
					recordMap.put(columnName, columnValue);
				}
				// ���������ݣ�map����ӵ��б���
				recordList.add(recordMap);
			}
			// �ر���Դ
			close(resultSet, pstmt, conn);
		} catch (Exception e) {

		}

		return recordList;
	}
}
