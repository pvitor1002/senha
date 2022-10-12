package org.example.senha.adapters.events.mapper;

import br.com.PoC.ComandoSenha;
import org.example.senha.domain.entities.Senha;
import org.springframework.stereotype.Component;

@Component
public class SenhaMapper {

    public Senha map (ComandoSenha comandoSenha){
        return Senha.builder()
                .autenticacao(comandoSenha.getSenha())
                .build();
    }
}
