package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: guohezuzi
 * \* Date: 2018-06-25
 * \* Time: 下午8:22
 * \* Description:foo
 * \
 */
@Controller
public class foo {
    @RequestMapping("/test")
    public String test(){
        return "foo";
    }
}
