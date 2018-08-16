package cn.lhl.test.action;

import cn.lhl.module.domain.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/16.
 */
@RequestMapping("/test")
@Controller
public class TestCotroller {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test/test";
    }
}
