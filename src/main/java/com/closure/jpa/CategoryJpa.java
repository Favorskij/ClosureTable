package com.closure.jpa;

import com.closure.model.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional(readOnly = true)
public interface CategoryJpa extends JpaRepository<CategoryName, Long> {




    // 1
    // Получаем весь список потомков, внуков, правнуков и самого себя от текущей ноды id
    // SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent=3;
    @Query(value = "SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent=:id", nativeQuery = true)
    Collection<CategoryName> findChildren1(@Param("id") int id);
    /*

    +----+--------------------------------------+-------+----------+--------+
    | id | name                                 | level | children | parent |
    +----+--------------------------------------+-------+----------+--------+
    |  3 | Аксессуары и принадлежности          |     0 |        3 |      3 |
    |  7 | Аудио и видео аксессуары             |     0 |        7 |      3 |
    |  8 | Аксессуары для камер и фотоаппаратов |     0 |        8 |      3 |
    | 15 | 3D очки                              |     0 |       15 |      3 |
    | 16 | Антенны                              |     0 |       16 |      3 |
    | 17 | Наборы аксессуаров                   |     0 |       17 |      3 |
    | 18 | Сумки и кейсы                        |     0 |       18 |      3 |
    +----+--------------------------------------+-------+----------+--------+

     */



    // 2
    // Все тоже-самое, только исключает ссылку на самого себя.
    // SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent=3 AND t.children!=t.parent;
    @Query(value = "SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent=:id AND t.children!=t.parent", nativeQuery = true)
    Collection<CategoryName> findChildren2(@Param("id") int id);
    /*

    +----+--------------------------------------+-------+----------+--------+
    | id | name                                 | level | children | parent |
    +----+--------------------------------------+-------+----------+--------+
    |  7 | Аудио и видео аксессуары             |     0 |        7 |      3 |
    |  8 | Аксессуары для камер и фотоаппаратов |     0 |        8 |      3 |
    | 15 | 3D очки                              |     0 |       15 |      3 |
    | 16 | Антенны                              |     0 |       16 |      3 |
    | 17 | Наборы аксессуаров                   |     0 |       17 |      3 |
    | 18 | Сумки и кейсы                        |     0 |       18 |      3 |
    +----+--------------------------------------+-------+----------+--------+

     */



    // 3
    // Получаем весь список потомков, внуков и правнуков от текущей ноды id с глубиной потомков LEVEL
    // SELECT p.id, p.name, count(*) - l.level AS level FROM tree_Path t, (SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level;
    @Query(value = "SELECT p.id, p.name, count(*) - l.level AS level FROM tree_Path t, (SELECT count(*) -1 AS level FROM tree_path WHERE children = :id) l, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent = :id AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level;", nativeQuery = true)
    Collection<CategoryName> findChildren3(@Param("id") int id);
    /*

    +----+--------------------------------------+-------+
    | id | name                                 | level |
    +----+--------------------------------------+-------+
    |  7 | Аудио и видео аксессуары             |     2 |
    |  8 | Аксессуары для камер и фотоаппаратов |     2 |
    | 15 | 3D очки                              |     3 |
    | 16 | Антенны                              |     3 |
    | 17 | Наборы аксессуаров                   |     3 |
    | 18 | Сумки и кейсы                        |     3 |
    +----+--------------------------------------+-------+

    */



    // 4
    // Получаем весь список предков, дедушек, пра-дедушек, от текущей ноды id
    // SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=7;
    @Query(value = "SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=:id", nativeQuery = true)
    Collection<CategoryName> findAllParent1(@Param("id") int id);
    /*

    +----+-----------------------------+-------+----------+--------+
    | id | name                        | level | children | parent |
    +----+-----------------------------+-------+----------+--------+
    |  1 | Электроника                 |     0 |        7 |      1 |
    |  3 | Аксессуары и принадлежности |     0 |        7 |      3 |
    |  7 | Аудио и видео аксессуары    |     0 |        7 |      7 |
    +----+-----------------------------+-------+----------+--------+

     */



    // 5
    // Все тоже-самое, только исключает ссылку на самого себя.
    // SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=7 AND t.parent!=t.children;
    @Query(value = "SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=:id AND t.parent!=t.children", nativeQuery = true)
    Collection<CategoryName> findAllParent2(@Param("id") int id);
    /*

    +----+-----------------------------+-------+----------+--------+
    | id | name                        | level | children | parent |
    +----+-----------------------------+-------+----------+--------+
    |  1 | Электроника                 |     0 |        7 |      1 |
    |  3 | Аксессуары и принадлежности |     0 |        7 |      3 |
    +----+-----------------------------+-------+----------+--------+

    */


    // 6
    // Получаем весь список предков, пра-предков от текущей ноды id с глубиной предков LEVEL
    // SELECT p.id, p.name, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) AS LEVEL FROM tree_Path t, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=7 AND t.children != t.parent) p WHERE p.parent = t.parent GROUP BY p.id, p.name;
    @Query(value = "SELECT p.id, p.name, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) AS LEVEL FROM tree_Path t, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=:id AND t.children != t.parent) p WHERE p.parent = t.parent GROUP BY p.id, p.name", nativeQuery = true)
    Collection<CategoryName> findAllParent3(@Param("id") int id);
    /*

    +----+-----------------------------+-------+
    | id | name                        | LEVEL |
    +----+-----------------------------+-------+
    |  1 | Электроника                 |     1 |
    |  3 | Аксессуары и принадлежности |     2 |
    +----+-----------------------------+-------+

    */



    // 7
    // Получаем весь список потомков, кроме внуков и правнуков от текущей ноды id с глубиной потомков LEVEL
    // SELECT p.id, p.name, count(*) - l.level AS levelx FROM tree_path t, ( SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (  SELECT * FROM category_name f INNER JOIN tree_path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level HAVING levelx = 2;
    // SELECT p.id, p.name, count(*) - l.level AS levelx FROM tree_path t, ( SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (  SELECT * FROM category_name f INNER JOIN tree_path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level HAVING count(*) - l.level = 2;
    @Query(value = "SELECT p.id, p.name, count(*) - l.level AS levelx FROM tree_path t, ( SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (  SELECT * FROM category_name f INNER JOIN tree_path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level HAVING count(*) - l.level = 2", nativeQuery = true)
    Collection<CategoryName> findChildren4(@Param("id") int id);
    /*

    +----+--------------------------------------+--------+
    | id | name                                 | levelx |
    +----+--------------------------------------+--------+
    |  7 | Аудио и видео аксессуары             |      2 |
    |  8 | Аксессуары для камер и фотоаппаратов |      2 |
    +----+--------------------------------------+--------+

    */




    // Удаление узла из дерева
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM category_name WHERE id IN (SELECT children FROM tree_Path WHERE parent=:id)", nativeQuery = true)
    void deleteNode(@Param("id") int id);




    // Получаем id последней записи в таблице. Это только что сохранённой таблицы
    @Query(value = "SELECT id FROM category_name ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    Long findTheLastOne ();

}
