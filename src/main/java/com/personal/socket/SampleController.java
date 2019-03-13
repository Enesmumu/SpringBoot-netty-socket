package com.personal.socket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@RequestMapping("/hello")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
	
	@RequestMapping("/test")
    @ResponseBody
    void test() {
		MessageEventHandler.sendBuyLogEvent();
    }
}
