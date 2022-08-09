package com.ll.exam.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//스프링부트 스타터 웹에 어노테이션이 다 정의되어있다.
@Controller
public class MainController {
    //싱글톤(i 생명주기가 늘어남)
    private int i;
    private String input;

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
                """.formatted(a + b);
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
                """.formatted(a - b);
    }

    @GetMapping("/plus1")
    @ResponseBody
    public int showPlus(int a, int b) {
        return a + b;
    }

    //서블릿방식으로 요청과 응답 비교
    @GetMapping("/plus2")
    @ResponseBody
    public void showPlus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));

        resp.getWriter().append(a + b + "");
    }

    @GetMapping("/minus1")
    @ResponseBody
    public int showMinus1(int a, int b) {
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        i++;
        return i;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    //강사님은 IntStream으로 구현했네. 만약 객체형태의 Integer로 받았더라면 매개변수 dan을 다시 final을 붙여서 정의한다.
//    public String showGugudan(int dan, int limit){//Integer 즉, 객체를 넣는다면 null 허용
//        int i = 1;
//        for( i = 0; i<limit; i++)
//            System.out.printf("%d * %d = %d\n", dan, i, dan*i);
//        return """
//                %d * %d = %d
//                """.formatted(dan, i, dan*i);
//    }
    public String showGugudan(Integer dan, Integer limit) {
        if (limit == null) {
            limit = 9;
        }

        if (dan == null) {
            dan = 9;
        }
        Integer finalDan = dan; //변수에 파이널을 붙여도, 컴파일러는 이를 파이널을 명시한 것으로 안다.
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>\n"));
    }

    //mbti예제
    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name) {
        return switch (name) {
            case "홍길순" -> {
                char j = 'J';
                yield "INF" + j;
            }
            case "임꺽정" -> "ENFP";
            case "장희성", "홍길동" -> "INFP"; //or
            default -> "모름";
        };
    }

    @RequestMapping(value = "/saveSessionAge", method = {RequestMethod.POST})
    public String adminLogin(HttpServletRequest request, Model model, HttpSession session) throws Exception {

        String id = request.getParameter("10");
        session.setAttribute("id", id);                 // 세션에 값을 셋팅하는 방법
        input = (String) session.getAttribute("id"); // 세션에서 값을 가져오는 방법
        return """
                <body>
                <input type="text" value="%d"/>
                </body>
                """.formatted(input);
    }
    @GetMapping("/getSessionAge")
    @ResponseBody
    public String showSession() {
        return input;
    }

    //session값 저장
    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req){
        HttpSession session = req.getSession();
        //request안에 쿠키가 들어있다. 쿠키는 변수다. JSESSIONID
        session.setAttribute(name, value);


        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }
    //세션정보 얻기
    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name, HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값이 %s 입니다.".formatted(name, value);
    }
    private List<Article> articles = new ArrayList<>();
    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) {
        Article article = new Article(title, body);

//        ++articleCnt;
        articles.add(article);
//        return "%d번째 글이 등록되었습니다.".formatted(articleCnt);
        return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable int id) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id) // 1번
                .findFirst()
                .get();

        return article;
    }

    @Getter
    @AllArgsConstructor
    class Article {
        private static int articleCnt = 0;
        private final int id;
        private final String articleTitle;
        private final String articleBody;

        public Article(String articleTitle, String articleBody){//매개변수로 필드를  넣어줘야하네.
            this(++articleCnt, articleTitle, articleBody); //인자 개수가 다른 걸 에러없이 넣으려고 한다면, final을 붙이자.
        }
    }
}

