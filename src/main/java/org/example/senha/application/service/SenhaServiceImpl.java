package org.example.senha.application.service;

import org.example.senha.domain.entities.Senha;
import org.springframework.stereotype.Service;

@Service
public class SenhaServiceImpl implements SenhaService {
    @Override
    public Senha execute(Senha senha) {
        return senha;
    }
}
