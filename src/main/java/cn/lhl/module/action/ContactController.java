package cn.lhl.module.action;

import java.util.List;
import java.util.Map;

import cn.lhl.util.JsonResponsePojo;
import cn.lhl.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.lhl.module.dao.ContactDao;
import cn.lhl.module.domain.Contact;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/homeJson", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String homeJson() {
		String res = null;
		try {
			res = JsonUtil.objectToJsonString(new JsonResponsePojo(200, "OK", contactDao.findAll()));
		} catch (Exception e) {
			e.printStackTrace();
			res = "{\"code\":500,\"text\":\"JSON解析异常\",\"result\":[]}";
		}
		return res;
	}

	@RequestMapping(value = "/saveJson", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveJson(Contact contact) {
		System.out.println(contact);
		contactDao.save(contact);
		String res = null;
		try {
			res = JsonUtil.objectToJsonString(new JsonResponsePojo(200, "OK", null));
		} catch (Exception e) {
			e.printStackTrace();
			res = "{\"code\":500,\"text\":\"JSON解析异常\",\"result\":[]}";
		}
		return res;
	}

	@RequestMapping(value = "/saveJson2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")//, produces = "application/json;charset=UTF-8"
	@ResponseBody
	public JsonResponsePojo saveJson2(Contact contact) {
		System.out.println(contact);
		return new JsonResponsePojo(200, "OK正常", null);
	}
}
