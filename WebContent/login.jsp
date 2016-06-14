<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br">
  <head>
    <jsp:include page="comum/head.html" />
    <link rel="stylesheet" type="text/css" href="css/login.css"></link>
  </head>
  <body>
    <div id="conteudo">
      <div id="principal" class="module">
        <div>
          <form method="post" action="autenticar">
            <fieldset>
              <legend>Bem vindo ao Tizeeter</legend>
              <label for="login">Username:</label>
              <input type="text" name="login" />
              <label for="senha">Password:</label>
              <input type="password" name="senha" />
              <label><input type="checkbox" value="s">Manter logado</label>
              <input id="submit" type="submit" value="Login" />
            </fieldset>
          </form>
        </div>
        <hr />
        <p>
          N&atilde;o possui conta? Registre-se agora!<br />
          <a href="cadastrar.jsp">Registrar</a>
        </p>
      </div>
    </div>
  </body>
</html>
