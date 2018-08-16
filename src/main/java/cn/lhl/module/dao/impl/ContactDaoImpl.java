package cn.lhl.module.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.lhl.module.dao.ContactDao;
import cn.lhl.module.domain.Contact;

@Repository
public class ContactDaoImpl implements ContactDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public List<Contact> findAll() {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, name, salary ");
		sql.append("from contact ");
		sql.append("where 1=1 ");
		List<Contact> list = jdbc.query(sql.toString(), new RowMapper<Contact> () {
			public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
				Contact contact = new Contact();
				contact.setId(rs.getInt(1));
				contact.setName(rs.getString(2));
				contact.setSalary(rs.getDouble(3));
				return contact;
			}
		});
		return list;
	}

	@Override
	public int save(Contact contact) {
		System.out.println("saving..."+contact);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into contact ");
		sql.append("(id, name, salary) ");
		sql.append("values ");
		sql.append("(?, ?, ?) ");
		int res = jdbc.update(sql.toString(), contact.getId(), contact.getName(), contact.getSalary());
		return res;
	}
	
}
