package com.me.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class itemController {

    @GetMapping("/api/items")
    public List<String> getItems(){

        List<String> items = new ArrayList<>();
        items.add("alpha");
        items.add("beta");
        items.add("gamma");

        return items;

    }
}
