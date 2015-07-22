package org.online.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.online.dao.AcountsDAO;
import org.online.model.Acounts;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class AcountsService {
	private AcountsDAO acountsDAO;
	private DriverManagerDataSource dataSource;
	public void save(Acounts entity) {
		acountsDAO.save(entity);
	}

	public void update(Acounts entity) {
		acountsDAO.update(entity);
	}

	public void delete(Acounts entity) throws SQLException {
		String username=entity.getAcname();
		String sql0="delete from deals where acdeid='"+username+"'";
		String sql1="delete from tablelist where tadirid='"+username+"' or tadirid in(select dirid from tabledir where ownername='"+username+"')";
		String sql2="delete from tabledir where ownername='"+username+"'";
		//System.out.println(sql1+"##############");
		Connection con=dataSource.getConnection();
		Statement st=(Statement) con.createStatement();
		ResultSet rs=st.executeQuery("select taid from tablelist where tadirid='"+username+"' or tadirid in(select dirid from tabledir where ownername='"+username+"')");
		List<String> tmp=new ArrayList<String>();
		while(rs.next())
		{
			tmp.add(rs.getString("taid"));
		}
		rs.close();
		st.addBatch(sql0);
		st.addBatch(sql1);
		st.addBatch(sql2);
		Iterator<String> it=tmp.iterator();
		while(it.hasNext())
		{
			st.addBatch("drop table `"+it.next()+"`");
		}	
		st.executeBatch();
		acountsDAO.delete(entity);
	}

	public List<Acounts> findByAcorgid(String acorgid, int startIndex) {
		return acountsDAO.findByAcorgid(acorgid, startIndex);
	}
	public Acounts findeById(String id)
	{
		return acountsDAO.findById(id);
	}
	public AcountsDAO getAcountsDAO() {
		return acountsDAO;
	}

	public void setAcountsDAO(AcountsDAO acountsDAO) {
		this.acountsDAO = acountsDAO;
	}

	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
}
