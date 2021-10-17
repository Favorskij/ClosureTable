package com.closure.controller;

import com.closure.jpa.CategoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {


    @Autowired
    CategoryJpa categoryJpa;



    @GetMapping(value = "/")
    public String home () {
        return "/home";
    }



//    @ResponseBody
//    @GetMapping(value = "/{id}")
//    public String home (@PathVariable("id") int id) {
//
//        Collection<CategoryName> categoryNames1 = categoryNameJpa.findChildren1(id);
//
//        for (CategoryName name: categoryNames1){
//
//            System.out.println(name.getName());
//
//        }
//
//        System.out.println("2 ===================");
//
//        Collection<CategoryName> categoryNames2 = categoryNameJpa.findChildren2(id);
//
//        for (CategoryName name: categoryNames2){
//
//            System.out.println(name.getName());
//        }
//
//
//        System.out.println("2 ===================");
//
//
//        Collection<CategoryName> categoryNames3 = categoryNameJpa.findChildren3(id);
//
//        for (CategoryName name: categoryNames3){
//
//            System.out.println(name.getName());
//        }
//
//        System.out.println("3 ===================");
//
//        Collection<CategoryName> categoryNames4 = categoryNameJpa.findAllParent1(id);
//
//        for (CategoryName name: categoryNames4){
//
//            System.out.println(name.getName());
//        }
//
//        System.out.println("4 ===================");
//
//        Collection<CategoryName> categoryNames5 = categoryNameJpa.findAllParent2(id);
//
//        for (CategoryName name: categoryNames5){
//
//            System.out.println(name.getName() + " == " + name.getLevel());
//
//        }
//
//
//        System.out.println("5 ===================");
//
//        Collection<CategoryName> categoryNames6 = categoryNameJpa.findAllParent3(id);
//
//        for (CategoryName path: categoryNames6){
//
//
//            System.out.println(path.getName());
//        }
//
//
//
//
//        return "home";
//    }
//
//
//
//    @ResponseBody
//    @GetMapping(value = "/delete/{id}")
//    public String delete (@PathVariable("id") int id) {
//
//        System.out.println("6 ===================");
//
//        categoryNameJpa.deleteNode(id);
//
//
//        return "delete";
//    }



}
