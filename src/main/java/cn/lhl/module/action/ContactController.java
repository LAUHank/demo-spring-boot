package cn.lhl.module.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.lhl.module.dao.ContactDao;
import cn.lhl.module.domain.Contact;

@RequestMapping("/module")
@Controller
public class ContactController {
	@Autowired
	private ContactDao contactDao;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Map<String, Object> model) {
		List<Contact> list = contactDao.findAll();
		model.put("contacts", list);
		return "module/home";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Contact contact) {
		int res = contactDao.save(contact);
		System.out.println("影响了"+res+"行");
		return "redirect:/module/home";
	}
}
