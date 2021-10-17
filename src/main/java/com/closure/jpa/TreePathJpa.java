package com.closure.jpa;

import com.closure.model.TreePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TreePathJpa extends JpaRepository<TreePath, Long> {

    // Сохранение узла с привязкой всех потомков
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tree_path(parent, children) SELECT a.parent, d.children FROM (SELECT parent FROM tree_Path WHERE children=:parentId) a, (SELECT children FROM tree_path WHERE parent=:childId) d", nativeQuery = true)
    void saveNode(@Param("parentId") Long parentId, @Param("childId") Long childId);

}
