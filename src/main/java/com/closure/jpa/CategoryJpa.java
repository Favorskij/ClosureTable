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

    +----+--------------+-------+----------+--------+
    | id | name         | level | children | parent |
    +----+--------------+-------+----------+--------+
    |  3 | Сенсорные    |     0 |        3 |      3 |
    |  4 | С подсветкой |     0 |        4 |      3 |
    |  5 | На колёсиках |     0 |        5 |      3 |
    +----+--------------+-------+----------+--------+

     */



    // 2
    // Все тоже-самое, только исключает ссылку на самого себя.
    // SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent=3 AND t.children!=t.parent;
    @Query(value = "SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent=:id AND t.children!=t.parent", nativeQuery = true)
    Collection<CategoryName> findChildren2(@Param("id") int id);
    /*

    +----+--------------+-------+----------+--------+
    | id | name         | level | children | parent |
    +----+--------------+-------+----------+--------+
    |  4 | С подсветкой |     0 |        4 |      3 |
    |  5 | На колёсиках |     0 |        5 |      3 |
    +----+--------------+-------+----------+--------+

     */



    // 3
    // Получаем весь список потомков, внуков и правнуков от текущей ноды id с глубиной потомков LEVEL
    // SELECT p.id, p.name, count(*) - l.level AS level FROM tree_Path t, (SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level;
    @Query(value = "SELECT p.id, p.name, count(*) - l.level AS level FROM tree_Path t, (SELECT count(*) -1 AS level FROM tree_path WHERE children = :id) l, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.children WHERE t.parent = :id AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level;", nativeQuery = true)
    Collection<CategoryName> findChildren3(@Param("id") int id);
    /*

    +----+--------------+-------+
    | id | name         | level |
    +----+--------------+-------+
    |  4 | С подсветкой |     2 |
    |  5 | На колёсиках |     2 |
    +----+--------------+-------+

    */



    // 4
    // Получаем весь список предков, дедушек, пра-дедушек, от текущей ноды id
    // SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=7;
    @Query(value = "SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=:id", nativeQuery = true)
    Collection<CategoryName> findAllParent1(@Param("id") int id);
    /*

    +----+--------------+-------+----------+--------+
    | id | name         | level | children | parent |
    +----+--------------+-------+----------+--------+
    |  1 | Электроника  |     0 |        7 |      1 |
    |  2 | Телевизоры   |     0 |        7 |      2 |
    |  6 | Антибликовые |     0 |        7 |      6 |
    |  7 | Супертонкий  |     0 |        7 |      7 |
    +----+--------------+-------+----------+--------+

     */



    // 5
    // Все тоже-самое, только исключает ссылку на самого себя.
    // SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=7 AND t.parent!=t.children;
    @Query(value = "SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=:id AND t.parent!=t.children", nativeQuery = true)
    Collection<CategoryName> findAllParent2(@Param("id") int id);
    /*

    +----+--------------+-------+----------+--------+
    | id | name         | level | children | parent |
    +----+--------------+-------+----------+--------+
    |  1 | Электроника  |     0 |        7 |      1 |
    |  2 | Телевизоры   |     0 |        7 |      2 |
    |  6 | Антибликовые |     0 |        7 |      6 |
    +----+--------------+-------+----------+--------+

    */


    // 6
    // Получаем весь список предков, пра-предков от текущей ноды id с глубиной предков LEVEL (Исключает сам себя)
    // SELECT p.id, p.name, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) AS LEVEL FROM tree_Path t, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=7 AND t.children != t.parent) p WHERE p.parent = t.parent GROUP BY p.id, p.name;
    @Query(value = "SELECT p.id, p.name, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) AS LEVEL FROM tree_Path t, (SELECT * FROM category_name f INNER JOIN tree_Path t ON f.id = t.parent WHERE t.children=:id AND t.children != t.parent) p WHERE p.parent = t.parent GROUP BY p.id, p.name", nativeQuery = true)
    Collection<CategoryName> findAllParent3(@Param("id") int id);
    /*

    +----+--------------+-------+
    | id | name         | LEVEL |
    +----+--------------+-------+
    |  1 | Электроника  |     1 |
    |  2 | Телевизоры   |     2 |
    |  6 | Антибликовые |     3 |
    +----+--------------+-------+

    */



    // 7
    // Получаем весь список потомков, кроме внуков и правнуков от текущей ноды id с глубиной потомков LEVEL
    // SELECT p.id, p.name, count(*) - l.level AS levelx FROM tree_path t, ( SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (  SELECT * FROM category_name f INNER JOIN tree_path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level HAVING levelx = 2;
    // SELECT p.id, p.name, count(*) - l.level AS levelx FROM tree_path t, ( SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (  SELECT * FROM category_name f INNER JOIN tree_path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level HAVING count(*) - l.level = 2;
    @Query(value = "SELECT p.id, p.name, count(*) - l.level AS levelx FROM tree_path t, ( SELECT count(*) -1 AS level FROM tree_path WHERE children = 3) l, (  SELECT * FROM category_name f INNER JOIN tree_path t ON f.id = t.children WHERE t.parent = 3 AND t.children!=t.parent) p WHERE p.children = t.children GROUP BY p.id, p.name, l.level HAVING count(*) - l.level = 2", nativeQuery = true)
    Collection<CategoryName> findChildren4(@Param("id") int id);
    /*

    +----+--------------+--------+
    | id | name         | levelx |
    +----+--------------+--------+
    |  4 | С подсветкой |      2 |
    |  5 | На колёсиках |      2 |
    +----+--------------+--------+

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
