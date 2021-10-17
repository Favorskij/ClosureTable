package com.closure.controller;

import com.closure.model.CategoryName;
import com.closure.model.TreePath;
import com.closure.repository.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

// Данный класс служит для добавления узла в дерево или создания нового деева.
@Controller
public class SaveCategory {

    @Autowired
    ServiceJpa serviceJpa;

    @ResponseBody
    @GetMapping(value = "/saveCategory")
    public String saveCategory () {



        // Создаём категорию
        CategoryName categoryName = new CategoryName();
        categoryName.setName("test");

        // Сохраняем категорию
        serviceJpa.saveCategoryName(categoryName);

        // Получаем id последней записи в таблице. Это только что сохранённой таблицы ↑
        Long idCategory = serviceJpa.findTheLastOne();

        // Получаем только что созданную категорию по id ↑
        Optional<CategoryName> optionalCategoryName = serviceJpa.findById(idCategory);

        // Создаём новый узел Node
        TreePath treePath = new TreePath();
        // Замыкаем самого на себя только что полученную категорию ↑
        treePath.setChildren(optionalCategoryName.orElseThrow());
        treePath.setParent(optionalCategoryName.orElseThrow());
        // Сохраняем узел Node
        serviceJpa.save(treePath);

        // Сохранение узла с привязкой всех потомков
        serviceJpa.saveNode(28L, idCategory);





        return "Ok";
    }

}
