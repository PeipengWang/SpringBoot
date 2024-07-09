package com.wpp.controller;

import com.wpp.pojo.Measure;
import com.wpp.service.impl.MeasureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class MeasureController {

    @Autowired
    MeasureServiceImpl measureService;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("name", "Spring Boot");
        return "index";
    }

    @GetMapping(value = "/getAllMeasure")
    @ResponseBody
    public String  getAllMeasure(){
        List<Measure> allMeasure = measureService.getAllMeasure();
        for (Measure measure : allMeasure){
            System.out.println(measure);
        }
        return allMeasure.toString();
    }
}
