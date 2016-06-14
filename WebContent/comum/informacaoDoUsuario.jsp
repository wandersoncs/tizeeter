<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="nav" class="module">

	<div class="userdata">
		<a href="/nome_do_usuario"><img class="userphoto"
			src="img/user.png" alt="foto do usuario" /></a>

		<a href="logout">Sair</a>

	</div>

	<div class="userstats">
		<a class="complete_name" href="${usuario.nomeDeUsuario}">${usuario.nome}</a>
		<br /> <a class="username" href="${usuario.nomeDeUsuario}">@${usuario.nomeDeUsuario}</a>
		<br /> <a href="/nome_do_usuario">Zeets 300</a> <a href="/seguindo">Seguindo
			100</a> <a href="/seguidores">Seguidores 100</a>
	</div>
</div>
