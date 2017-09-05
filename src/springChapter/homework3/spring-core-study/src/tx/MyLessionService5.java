package tx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class MyLessionService5 {
	@Autowired
	private DataSource dataSource;

	public void autoAddSessions() throws Exception {
		System.out.println(" call addNewLession begin ");
		addNewLession("leaderus.java",9999);
	}

	
	 
	
	public void addNewLession(String lessionName, int price) throws Exception {
		Connection conn = DataSourceUtils.getConnection(dataSource);
		try {
			String sql = "insert into T_LESSION(name,price) values (?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, lessionName);
			ps.setInt(2, price);
			ps.executeUpdate();
			ps.close();
		} finally {
			DataSourceUtils.releaseConnection(conn, dataSource);
			// DataSourceUtils.doCloseConnection(conn, dataSource);
		}
		throw new Exception("Guess Transaction commited ?");
	}

	
	

	public void queryLessions() throws SQLException {
		Connection conn = DataSourceUtils.getConnection(dataSource);
		try {
			String sql = "select * from T_LESSION";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("Course " + rs.getString(1) + " price " + rs.getInt(2));
			}
			ps.close();
		} finally {
			DataSourceUtils.releaseConnection(conn, dataSource);
			// DataSourceUtils.doCloseConnection(conn, dataSource);
		}
	}
}
// CREATE TABLE `t_lession` (
// `name` varchar(80) NOT NULL,
// `price` bigint(20) NOT NULL,
// `id` int(11) AUTO_INCREMENT,
// PRIMARY KEY (`id`)
// ) ENGINE=InnoDB ;
