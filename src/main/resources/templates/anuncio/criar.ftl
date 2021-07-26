<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <#include "/components/meta.ftl"/>
        <title>allPet - Criar anúncio</title>
    </head>
    <body>
        <#include "/components/header.ftl"/>
        <#include "/components/user-space.ftl"/>

        <#if sucesso??>
            <#if sucesso>
                <p class="mensagem-sucesso">Anúncio criado com sucesso</p>
                <a href="/anuncio/${anuncioId}"></a>
            <#else>
                <p class="mensagem-erro">Ocorreu um erro na criação do anúncio</p>
            </#if>
        </#if>
        <form action="anuncio/criar" method="post">
            Título: <input type="text" name="titulo" />
            Descrição: <textarea name="descricao"></textarea>
            Endereço: <input type="text" name="endereco" />
            Cidade: <input type="text" name="cidade" />
            Telefone: <input type="tel" name="telefone" />
            E-mail: <input type="email" name="email" />
            Animal:
            <select name="animal">
                <#list animais as animal>
                    <option>${animal?capitalize}</option>
                </#list>
            </select>
            <input type="submit" />
        </form>
    </body>
</html>
