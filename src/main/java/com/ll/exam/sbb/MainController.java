package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//스프링부트 스타터 웹에 어노테이션이 다 정의되어있다.
@Controller
public class MainController {

    @RequestMapping("/sbb")
    //아래 함수의 리턴값을 그대로 브라우저에 표시
    //아래 함수의 리턴값을 문자열화해서 브라우저 응답의 바디에 담는다, URL 요청에 대한 응답으로 문자열을 리턴하라는 의미
    @ResponseBody
    public String index() {
        //서버에서 출력(콘솔)
        System.out.println("Hello");
        //먼 미래에 브라우저에서 보여짐
        return "index 안녕하세유 반가워요";
    }
}
