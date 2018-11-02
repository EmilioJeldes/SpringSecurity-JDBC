package cl.ejeldes.springsecuritymvc.controller;

import cl.ejeldes.springsecuritymvc.entities.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping({"/public", "/"})
    public Hello publicHello() {
        return getHello("This is a public message", "The public");
    }

    @GetMapping("/private")
    public Hello privateHello() {
        return getHello("This is a private message", "A private user");
    }

    @GetMapping("/admin")
    public Hello adminHello() {
        return getHello("This is a message for the admin", "The admin");
    }

    private Hello getHello(String s, String s2) {
        Hello hello = new Hello();
        hello.setMessage(s);
        hello.setFrom("The controller");
        hello.setTo(s2);

        return hello;
    }
}
