-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Окт 17 2021 г., 06:09
-- Версия сервера: 8.0.25
-- Версия PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `closure`
--

-- --------------------------------------------------------

--
-- Структура таблицы `category_name`
--

CREATE TABLE `category_name` (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_ru_0900_ai_ci DEFAULT NULL,
  `level` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_ru_0900_ai_ci;

--
-- Дамп данных таблицы `category_name`
--

INSERT INTO `category_name` (`id`, `name`, `level`) VALUES
(1, 'Электроника', 0),
(2, 'Телевизоры', 0),
(3, 'Сенсорные', 0),
(4, 'С подсветкой', 0),
(5, 'На колёсиках', 0),
(6, 'Антибликовые', 0),
(7, 'Супертонкий', 0),
(8, 'Настенный', 0),
(9, 'Телефон', 0),
(10, 'Ракушка', 0),
(11, 'Кнопочный', 0),
(12, 'Сенсорный', 0),
(13, 'Выдвижной', 0),
(14, 'Автоматический', 0),
(15, 'Ручной', 0),
(16, 'Для дома', 0),
(17, 'Для ванной', 0),
(18, 'Коврик', 0),
(19, 'С животными', 0),
(20, 'Обструкция', 0),
(21, 'Мыльница', 0),
(22, 'С отверстиями', 0),
(23, 'Прозрачные', 0),
(24, 'Для кухни', 0),
(25, 'Сковородки', 0),
(26, 'С ручками', 0),
(27, 'Антипригарные', 0),
(28, 'кастрюли', 0),
(29, 'Паровые', 0),
(30, 'С крышками', 0);

-- --------------------------------------------------------

--
-- Структура таблицы `tree_path`
--

CREATE TABLE `tree_path` (
  `children` bigint NOT NULL,
  `parent` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_ru_0900_ai_ci;

--
-- Дамп данных таблицы `tree_path`
--

INSERT INTO `tree_path` (`children`, `parent`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2),
(3, 3),
(4, 3),
(5, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 6),
(8, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 9),
(11, 9),
(12, 9),
(13, 9),
(14, 9),
(15, 9),
(10, 10),
(11, 10),
(12, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 13),
(15, 13),
(14, 14),
(15, 15),
(16, 16),
(17, 16),
(18, 16),
(19, 16),
(20, 16),
(21, 16),
(22, 16),
(23, 16),
(24, 16),
(25, 16),
(26, 16),
(27, 16),
(28, 16),
(29, 16),
(30, 16),
(17, 17),
(18, 17),
(19, 17),
(20, 17),
(21, 17),
(22, 17),
(23, 17),
(18, 18),
(19, 18),
(20, 18),
(19, 19),
(20, 20),
(21, 21),
(22, 21),
(23, 21),
(22, 22),
(23, 23),
(24, 24),
(25, 24),
(26, 24),
(27, 24),
(28, 24),
(29, 24),
(30, 24),
(25, 25),
(26, 25),
(27, 25),
(26, 26),
(27, 27),
(28, 28),
(29, 28),
(30, 28),
(29, 29),
(30, 30);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `category_name`
--
ALTER TABLE `category_name`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `tree_path`
--
ALTER TABLE `tree_path`
  ADD PRIMARY KEY (`children`,`parent`),
  ADD KEY `FK_PARENT` (`parent`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `category_name`
--
ALTER TABLE `category_name`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `tree_path`
--
ALTER TABLE `tree_path`
  ADD CONSTRAINT `FK_CHILDREN` FOREIGN KEY (`children`) REFERENCES `category_name` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_PARENT` FOREIGN KEY (`parent`) REFERENCES `category_name` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
