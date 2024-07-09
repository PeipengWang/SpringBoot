package com.wpp.controller;

import com.wpp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getuser/{id}", method = RequestMethod.GET)
    public String getAllUser(@PathVariable("id") int id){
        System.out.println("用户信息");
        userService.testTransactionUser(id);
        userService.testTransactionUser2(id);
        return "success1";
    }
}
