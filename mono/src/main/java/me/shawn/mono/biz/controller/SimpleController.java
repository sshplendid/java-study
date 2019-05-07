package me.shawn.mono.biz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/simple")
public class SimpleController {

    @GetMapping("/greeting")
    public String getGreeting(@RequestParam(defaultValue = "world") String name) {
        return String.format("Hello, %s!", name);
    }

    @GetMapping("/greeting.json")
    public Map<String, Object> getGreetingMap(@RequestParam(defaultValue = "world") String name) {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("content", String.format("Hello, %s!", name));

        return map;
    }
}
