<!DOCTYPE html>
<html>
    <head>
        <title>Gusar</title>
    </head>

    <body>
        <h1>Горячо приветствую, Гусар!</h1><hr>
        <#if user.email??>
            <h3>Имя пользователя: <i>${user.name}</i></h3>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Выйти"/>
            </form>
            <p><a href="/user/${user.id}">Мой профиль</a></p>
            <#if user.isAdmin()>
                <a href="/admin">Панель администратора</a>
            </#if>
            <#if user.isManager()>
                <a href="/manager">Панель менеджера</a>
            </#if>
        <#else>
            <a href="/login">Войти</a></h1>
        </#if>
        <hr>
        <h3>Наши Барберы</h3>
        <hr>
        <form action="/" method="get">
            Найти мастера по имени: <input type="text" name="barbName"><br>
            <input type="submit" value="Поиск"/>
        </form>
        <#list barbers as barber>
            <div>
                <p><img src="/images/${barber.previewImageId}" height="100px"/><b>${barber.barbName}</b> | <a href="/barber/${barber.id}">Подробнее... </a>
                <#if user.isManager()> | <a href="/manager/barber/delete/${barber.id}">удалить</a></#if><br></p>
            </div>
            <#else>
            <h4>Берберы не найдены</h4>
        </#list>

        <#if user.isManager()><form action="/barber/add" method="post" enctype="multipart/form-data">
            Имя барбера: <input type="text" name="barbName"/>
            <br>
            Стаж: <input type="number" name="experience"/>
            <br>
            Текущий рейтинг: <input type="number" name="rating" value="0" min="0" max="5"/>
            <br>
            Описание: <input type="text" name="description"/>
            <br>
            Первая фотография: <input type="file" name="file1"/>
            <br>
            Вторая фотография: <input type="file" name="file2"/>
            <br>
            Третья фотография: <input type="file" name="file3"/>
            <br>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="добавить мастера"/>
            <br>
        </form></#if>
        <br>
        <br>
    </body>
</HTML>