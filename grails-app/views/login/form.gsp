<%--
  Created by IntelliJ IDEA.
  User: igor
  Date: 5/7/16
  Time: 1:23 PM
    // ToDo How to center the consist of the page using CSS and Responsive Web Design
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    %{--<meta name="layout" content="main">--}%

    <style>
    body {
        position: relative;
    }

    .Absolute-Center {
        width: 20%;
        height: 50%;
        overflow: auto;
        margin: auto;
        position: absolute;
        top: 0; left: 0; bottom: 0; right: 0;
    }

    .field {
        clear: both;
        text-align: right;
        line-height: 25px;
    }

    .field label {
        float: left;
        padding-right: 10px;
    }

    div.main {
        float: left;
        text-align: end;
    }
    </style>
</head>

<body>
<div class="Absolute-Center">
    <h1>Login to Hubbub</h1>
    <g:hasErrors>
        <div class="errors">
            <g:renderErrors bean="${user}" as="list"/>
        </div>
    </g:hasErrors>
    <g:if test="${flash.message}">
        <div class="flash">${flash.message}</div>
    </g:if>
    <div class="main">
        <g:form action="signIn">
            <fieldset class="form">
                <legend>Login Form:</legend>

                <div class="field">
                    <label for="impersonateId">Login</label>
                        <g:textField name="impersonateId" value="${user?.impersonateId}"/>
                </div>

                <div class="field">
                    <label for="password">Password</label>
                    <g:passwordField name="password"/>
                </div>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="login" value="Login"/>
            </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>