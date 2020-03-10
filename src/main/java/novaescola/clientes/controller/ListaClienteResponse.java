package novaescola.clientes.controller;

import lombok.Data;
import novaescola.clientes.model.ClienteDTO;

import java.util.List;

@Data
public class ListaClienteResponse {

    public ListaClienteResponse(Long total, List<ClienteDTO> lista) {
        this.total = total;
        this.lista = lista;
    }

    private Long total;
    private List<ClienteDTO> lista;

}
