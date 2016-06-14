<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br">
  <head>
    <c:import url="/comum/head.html"/>
    <script type="text/javascript" src="js/tizeeter.js"></script>
  </head>
  <body>
    <c:import url="comum/cabecalho.jsp" />
    <div id="conteudo">
      <c:import url="comum/informacaoDoUsuario.jsp" />
      <div id="principal" class="module">
        <div id="tizeet_form">
          <form method="post" action="tizeet">
            <textarea name="tizeet" id="tizeet" rows="5"></textarea><br/>
            <span id="tizeet_size">140</span>
            <input id="submit" type="submit" disabled="disabled" />
          </form>
        </div>
        <c:forEach var="t" items="${ tizeets }">
          <p class="tizeet">
            <img src="img/user.png" alt="foto do dono do tweet" />
            <a class="complete_name" href="/">${ t.usuario.nome }</a>
            <a class="username">@</a>${ t.usuario.nomeDeUsuario }<br/>
            ${ t.conteudo }
            <span class="tizeet_date"><fmt:formatDate pattern="dd/MM/yyyy - HH:mm" value="${ t.data }" /></span>
          </p>
        </c:forEach>
      </div>
      <c:import url="comum/tendencias.jsp" />
      <c:import url="comum/rodape.jsp" />
    </div>
  </body>
</html>
