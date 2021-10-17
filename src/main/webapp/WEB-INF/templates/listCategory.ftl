<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Добавление категории</title>
</head>

<body>

<b>Добавление категории</b>

<br><br>


<form method="get">


<#--    <#list categoryNames as CategoryName>-->
<#--    -->
<#--    ${CategoryName.name}<td>${CategoryName.id}-->
<#--        -->
<#--        </#list>-->


<#--    <#list categoryNames as CategoryName>-->
<#--        ${CategoryName.id} ${CategoryName.name}-->
<#--    </#list>-->




        <label for="category">Выберите категорию:</label>
        <select id="category" name="category">

            <#list listCategory as CategoryName>

            <option value=${CategoryName.id}>${CategoryName.name}</option>

            </#list>

        </select>




        <input type="submit" value="Отправить" />

</form>

<br><br>

<a href="/"><b>/Главная</b></a><br>

</body>
</html>