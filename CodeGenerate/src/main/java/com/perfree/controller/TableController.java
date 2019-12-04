package com.perfree.controller;

import com.perfree.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/table")
public class TableController {
    private final static Logger logger = LoggerFactory.getLogger(TableController.class);
    @Autowired
    private TableService tableService;

    @RequestMapping("/queryAllTable")
    public void queryAllTable() {
    }
}
