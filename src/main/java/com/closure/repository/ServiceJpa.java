package com.closure.repository;

import com.closure.model.CategoryName;
import com.closure.model.TreePath;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public interface ServiceJpa {

    // Получаем название всех категорий
    Collection<CategoryName> findAll();




    // Сохраняем категорию
    void saveCategoryName(CategoryName categoryName);

    // Получаем id последней записи в таблице. Это только что сохранённой таблицы ↑
    Long findTheLastOne();

    // Получаем только что созданную категорию по id ↑
    Optional<CategoryName> findById(Long id);

    // Сохраняем узел Node
    void save(TreePath treePath);

    // Сохранение узла с привязкой всех потомков
    void saveNode(Long parentId, Long childId);


}
