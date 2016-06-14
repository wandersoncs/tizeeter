<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
  <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br">
    <head>
      <jsp:include page="comum/head.html" />
      <link rel="stylesheet" type="text/css" href="css/cadastrar.css"></link>
      <script type="text/javascript" src="js/cadastrar.js"></script>
    </head>
    <body>
      <div id="conteudo">
        <div id="principal" class="module">
          <form id="cadForm" action="usuario/novo" method="post">
            <h3> Informa&ccedil;&otilde;es da conta</h3>
            <label for="username">Nome do usu&aacute;rio:</label>
            <input id="username" name="username" type="text"/>
            <span class="error" id="username-error"></span>

            <label for="email">Email:</label>
            <input id="email" name="email" type="text"/>
            <span class="error" id="email-error"></span>

            <label for="password">Senha:</label>
            <input id="password" name="password" type="password"/>
            <span class="error" id="password-error"></span>

            <label for="conf-password">Confirma&ccedil;&atilde;o da Senha:</label>
            <input id="conf-password" name="conf-password" type="password"/>
            <span class="error" id="conf-password-error"></span>

            <h3> Informa&ccedil;&otilde;es pessoais</h3>
            <label for="name">Nome completo:</label>
            <input id="name" name="name" type="text"/>
            <span class="error" id="name-error"></span>

            <label for="address">Endere&ccedil;o:</label>
            <input id="address" name="address" type="text"/>
            <span class="error" id="address-error"></span>

            <label for="birthday">Data de nascimento</label>
            <select name="day">
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
              <option value="9">9</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
              <option value="16">16</option>
              <option value="17">17</option>
              <option value="18">18</option>
              <option value="19">19</option>
              <option value="20">20</option>
              <option value="21">21</option>
              <option value="22">22</option>
              <option value="23">23</option>
              <option value="24">24</option>
              <option value="25">25</option>
              <option value="26">26</option>
              <option value="27">27</option>
              <option value="28">28</option>
              <option value="29">29</option>
              <option value="30">30</option>
              <option value="31">31</option></option>
            </select> &nbsp; / &nbsp;
            <select name="month">
              <option value="1">janeiro</option>
              <option value="2">fevereiro</option>
              <option value="3">marÃ§o</option>
              <option value="4">abril</option>
              <option value="5">maio</option>
              <option value="6">junho</option>
              <option value="7">julho</option>
              <option value="8">agosto</option>
              <option value="9">setembro</option>
              <option value="10">outubro</option>
              <option value="11">novembro</option>
              <option value="12">dezembro</option>
            </select> &nbsp; / &nbsp;
            <select name="year">
              <option value="1980">1980</option>
              <option value="1990">1990</option>
              <option value="2000">2000</option>
            </select> &nbsp;
            <br/>
            <label name="sex">Sexo:</label>
            <label>
              <input id="sex" name="sex" type="radio" value="m"/>
              Masculino
            </label>
            <label>
              <input name="sex" type="radio" value="f"/>
              Feminino
            </label>
            <span class="error" id="sex-error"></span>
            <br/>
            <label>
              <input type="checkbox" name="news" value="sim">Desejar receber emails com as &uacute;ltimas novidades
              </label>
              <input id="submit" type="submit"/>
            </form>
          </div>

        </div>
      </body>
    </html>
