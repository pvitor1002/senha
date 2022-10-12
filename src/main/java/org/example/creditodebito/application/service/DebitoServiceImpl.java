package org.example.creditodebito.application.service;

import org.example.creditodebito.domain.entities.Debito;
import org.springframework.stereotype.Service;

@Service
public class DebitoServiceImpl implements DebitoService {
    @Override
    public Debito execute(Debito debito) {
        return debito;
    }
}
