package org.example.creditodebito.application.service;

import org.example.creditodebito.domain.entities.Credito;
import org.springframework.stereotype.Service;

@Service
public class CreditoServiceImpl implements CreditoService {
    @Override
    public Credito execute(Credito credito) {
        return credito;
    }
}
