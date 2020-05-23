<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith scripts=["js/main.js"] styles=["/css/main.css"]>

    <div class="flex flex-justify-content-center">
        <a class="flex box-128" href="/register"> Регистрация </a>
        <a class="flex box-128" href="/login"> Войти </a>
        <a class="flex box-128" href="/profile"> Профиль </a>

        <form class="flex box-128" action="/logout" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>
            <button class="flex box-128 logout-button">Выйти </button>
        </form>
    </div>
    <form action="/theme/add" method="post" id="theme-form">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
        </#if>
        <div>
            <label for="theme-form">Add new theme</label>
            <input type="text" id="title" name="title" value="">
            <button type="submit" name="button">ADD</button>
        </div>
    </form>
    </br>
    <div class="" id="categoryList">
    <#list items as item>
        <div class="card">
            <div class="flex flex-col align-center">
                <a href="/categories/${item.id}">
                    <div class="flex flex-col align-center  box-128">
                        <p>Title: ${item.title} -- Date: ${item.date} -- Author: ${item.user.fullname} -- Answer: ${item.count}</p>
<#--                        <p>Date: ${item.date}</p>-->
<#--                        <p>Author: ${item.user.email}</p>-->
<#--                        <p>Count: ${item.count}</p>-->

                    </div>
                </a>
            </div>
        </div>
    </#list>
    </div>
    <#include "partials/pagination.ftlh">

</@main.renderWith>