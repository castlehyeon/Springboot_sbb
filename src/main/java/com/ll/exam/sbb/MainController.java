package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//스프링부트 스타터 웹에 어노테이션이 다 정의되어있다.
@Controller
public class MainController {
    //싱글톤(i 생명주기가 늘어남)
    private int i;
    @RequestMapping("/sbb")
    //아래 함수의 리턴값을 그대로 브라우저에 표시
    //아래 함수의 리턴값을 문자열화해서 브라우저 응답의 바디에 담는다, URL 요청에 대한 응답으로 문자열을 리턴하라는 의미
    @ResponseBody
    public String index() {
        //서버에서 출력(콘솔)
        System.out.println("Hello");
        //먼 미래에 브라우저에서 보여짐
        return "index 안녕하세유 왜안돼";
    }
    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }
    @GetMapping("/plus")
    @ResponseBody
    public String plus() {
        return """
                <form method="POST" action="/plus_re">
                    <input type="number" name="a" placeholder="a" />
                    <input type="number" name="b" placeholder="b" />
                    <input type="submit" value="결과값" />
                </form>
                """;
    }
    @PostMapping("/plus_re")
    @ResponseBody
    public String plus_re(@RequestParam(defaultValue = "0") int a, int b) {
        return """
                <h1>결과값 : %d</h1>
                """.formatted(a+b);
    }
    @GetMapping("/minus")
    @ResponseBody
    public String minus() {
        return """
                <form method="POST" action="/minus_re">
                    <input type="number" name="a" placeholder="a" />
                    <input type="number" name="b" placeholder="b" />
                    <input type="submit" value="결과값" />
                </form>
                """;
    }
    @PostMapping("/minus_re")
    @ResponseBody
    public String minus_re(@RequestParam(defaultValue = "0") int a, int b) {
        return """
                <h1>결과값 : %d</h1>
                """.formatted(a-b);
    }
    @GetMapping("/plus1")
    @ResponseBody
    public int showPlus(int a, int b){
        return a + b;
    }
    @GetMapping("/minus1")
    @ResponseBody
    public int showMinus1(int a, int b){
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease(){
        i++;
        return i;
    }
}
