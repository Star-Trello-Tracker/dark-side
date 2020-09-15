package com.star_trello.darkside.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public HashMap<Integer, String> getTest() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Hello");
        map.put(2, "world");
        return map;
    }
}
