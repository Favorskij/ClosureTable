package com.closure.controller;

import com.closure.model.CategoryName;
import com.closure.repository.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

// Данный класс особо ни чего не делает, просто получает названия всех категорий
@Controller
public class ListCategory {

    @Autowired
    ServiceJpa serviceJpa;


    @GetMapping(value = "/listCategory")
    public ModelAndView listCategory() {

        // Получаем название все категории
        Collection<CategoryName> categoryNames = serviceJpa.findAll();

        for (CategoryName name: categoryNames){
            System.out.println(name.getId() + " == " + name.getName());
        }



        ModelAndView modelAndView = new ModelAndView("/listCategory");
        modelAndView.addObject("listCategory", categoryNames);
        // Возвращаем на страницу список всех категорий
        return modelAndView;
    }


}
