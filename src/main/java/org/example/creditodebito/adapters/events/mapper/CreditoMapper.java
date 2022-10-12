package org.example.creditodebito.adapters.events.mapper;

import br.com.PoC.ComandoCredito;
import org.example.creditodebito.domain.entities.Credito;
import org.springframework.stereotype.Component;

@Component
public class CreditoMapper {

    public Credito map (ComandoCredito comandoCredito){
        return Credito.builder()
                .conta(comandoCredito.getConta())
                .valor(comandoCredito.getValor())
                .agencia(comandoCredito.getAgencia())
                .dac(comandoCredito.getDac())
                .build();
    }
}
