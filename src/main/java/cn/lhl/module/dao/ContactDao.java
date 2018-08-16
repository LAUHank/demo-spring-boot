package cn.lhl.module.dao;

import java.util.List;

import cn.lhl.module.domain.Contact;

public interface ContactDao {
	List<Contact> findAll();
	int save(Contact contact);
}
