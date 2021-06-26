package org.example.senha.adapters.events.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private String id;
    private long origin;
    private int messageIndex;
    private String senha;
}
