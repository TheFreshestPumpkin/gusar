<!DOCTYPE html>
<html>
<head>
    <title>Gusar</title>
</head>
<body>
<h1>Gusar</h1><hr>
<br>
<p><a href="/">Вернуться на главную страницу</a></p>
<h3>Подробная информация о пользователе ${user.name}</h3>
<#if user1.isManager() || user1.isAdmin() || (user1.id?? && user1.id==user.id)>
        <b>Номер телефона: </b> ${user.phoneNumber}<br>
        <b>Email: </b> ${user.email}<br>
        <h4>Записи пользователя ${user.name}</h4>
        <#assign hours=["9","10","11","12","13","14","15","16","17","18","19","20","21","22"]>
        <#assign mins=["00","30"]>
        <#list appointments as appointment>
            <#assign x=0>
            <div>
                <p><b>${appointment.service}</b> У мастера с ${appointment.barber.barbName}
                <#list hours as hour>
                    <#list mins as min>
                        <#if x==appointment.timeId> <span> c </span> ${hour}:${min} </#if>
                        <#if x==(appointment.timeId + appointment.duration)> <span> до </span>${hour}:${min}</#if>
                        <#assign x++>
                    </#list>
                </#list>
                </p>
            </div>
            <#else>
            <h3>записей нет</h3>
        </#list>
<#else><p>У вас недостаточно прав для просмотра данной страницы</p>
</#if>
</body>
</html>