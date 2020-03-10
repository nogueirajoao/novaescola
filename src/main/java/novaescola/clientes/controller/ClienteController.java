package novaescola.clientes.controller;

import novaescola.clientes.model.ClienteDTO;
import novaescola.clientes.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public ListaClienteResponse buscarTodosClientes(@RequestParam Integer limite, @RequestParam Integer pagina) {
        return service.buscarTodos(limite, pagina);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCliente(cliente));
    }

    @PutMapping("/{id}")
    public ClienteDTO atualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteDTO cliente) {
        return service.atualizarCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable("id") Long id) {
        service.removerCliente(id);
    }

}
