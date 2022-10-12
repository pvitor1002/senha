package org.example.creditodebito.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Debito {
    private String conta;
    private String agencia;
    private String dac;
    private String valor;
}
