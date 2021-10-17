package com.closure.repository;

import com.closure.jpa.CategoryJpa;
import com.closure.jpa.TreePathJpa;
import com.closure.model.CategoryName;
import com.closure.model.TreePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ServiceJpaImpl implements ServiceJpa {

    @Autowired
    CategoryJpa categoryJpa;

    @Autowired
    TreePathJpa treePathJpa;



    // Получаем название всех категорий
    @Override
    public Collection<CategoryName> findAll() {
        return categoryJpa.findAll();
    }





    // Сохраняем категорию
    @Override
    @Transactional
    public void saveCategoryName(CategoryName categoryName) {
        categoryJpa.save(categoryName);
    }

    // Получаем id последней записи в таблице. Это только что сохранённой таблицы ↑
    @Override
    public Long findTheLastOne() {
        return categoryJpa.findTheLastOne();
    }

    // Получаем только что созданную категорию по id ↑
    @Override
    public Optional<CategoryName> findById(Long id) {
        return categoryJpa.findById(id);
    }

    // Сохраняем узел Node ↑
    @Override
    @Transactional
    public void save(TreePath treePath) {
        treePathJpa.save(treePath);
    }

    // Сохранение узла с привязкой всех потомков ↑
    @Override
    @Transactional
    public void saveNode(Long parentId, Long childId) {
        treePathJpa.saveNode(parentId, childId);
    }

}
