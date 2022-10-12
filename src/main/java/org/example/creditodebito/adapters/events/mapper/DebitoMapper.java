package org.example.creditodebito.adapters.events.mapper;

import br.com.PoC.ComandoDebito;
import org.example.creditodebito.domain.entities.Debito;
import org.springframework.stereotype.Component;

@Component
public class DebitoMapper {

    public Debito map (ComandoDebito comandoDebito){
        return Debito.builder()
                .conta(comandoDebito.getConta())
                .valor(comandoDebito.getValor())
                .agencia(comandoDebito.getAgencia())
                .dac(comandoDebito.getDac())
                .build();
    }
}
