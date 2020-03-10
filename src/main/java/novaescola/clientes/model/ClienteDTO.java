package novaescola.clientes.model;

import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String dataNascimento;

}
