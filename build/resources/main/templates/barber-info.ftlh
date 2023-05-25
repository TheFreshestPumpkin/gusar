<!DOCTYPE html>
<html>
    <head>
        <title>Gusar</title>
    </head>

    <body>
        <h1>Горячо приветствую, Гусар!</h1>
        <br>
        <p><a href="/">Вернуться на главную страницу</a></p>
        <h3>Подробная информация о мастере</h3>
        <br>
        <#list images as image>
            <img src="/images/${image.id}" height="100px"/>
            <br>
        </#list>
        <b>Имя барбера: </b>${barber.barbName}
        <br>
        <b>Стаж: </b>${barber.experience}
        <br>
        <b>Текущий рейтинг: </b>${barber.rating}
        <br>
        <b>Описание: </b>${barber.description}
        <p>Запись на сегодня:</p>
        <#assign hours=["9","10","11","12","13","14","15","16","17","18","19","20"]>
        <#assign mins=["00","30"]>
        <#assign x=0>
        <p>
        <#list hours as hour>
            <#list mins as min>
                <#if !schedule[x]>
                    <#if user.email??>  <a style="color: green;" href="/barber/${barber.id}/appointment/${x}">${hour}:${min}</a>
                    <#else> <a style="color: green;" href="/login">${hour}:${min}</a>
                    </#if>
                <#else>
                    <#if user.isManager() || user.isAdmin()>
                        <#list appointments as appointment>
                            <#if appointment.timeId==x>
                                <#assign y=x,appoint=appointment>
                                <a style="color: orange;" href="/user/${appoint.client.id}">${hour}:${min}</a>
                            </#if>
                        </#list>
                        <#if x!=y><a style="color: red;" href="/user/${appoint.client.id}">${hour}:${min}</a></#if>
                    <#else><span style="color: red;">${hour}:${min}</span>
                    </#if>
                </#if>
                <#assign x++>
            </#list>
        </#list>
        </p>
    </body>
</HTML>