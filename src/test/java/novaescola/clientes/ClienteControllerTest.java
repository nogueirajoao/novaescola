package novaescola.clientes;


import novaescola.clientes.controller.ListaClienteResponse;
import novaescola.clientes.model.Cliente;
import novaescola.clientes.model.ClienteDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void criarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João");
        clienteDTO.setEmail("joao@email.com");
        clienteDTO.setDataNascimento("21/01/1993");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ClienteDTO> entity = new HttpEntity<>(clienteDTO, headers);
        ResponseEntity<ClienteDTO> response = restTemplate
                .exchange(getRootUrl() + "/cliente",
                        HttpMethod.POST, entity, ClienteDTO.class);

        assertEquals("João", response.getBody().getNome());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    public void atualizarCliente() {
        int id = 1;
        Cliente cliente = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
        cliente.setNome("João Nogueira");
        cliente.setEmail("joao2@email.com");
        restTemplate.put(getRootUrl() + "/employees/" + id, cliente);
        Cliente updatedEmployee = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
        assertNotNull(updatedEmployee);
    }

    @Test
    public void deletarCliente() {
        int id = 1;
        Cliente cliente = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
        assertNotNull(cliente);
        restTemplate.delete(getRootUrl() + "/employees/" + id);
        try {
            cliente = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void buscarClientes() {
        Integer limite = 1;
        Integer pagina = 0;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<ListaClienteResponse> response = restTemplate
                .exchange(getRootUrl() + "/cliente?limite="+limite+"&pagina="+pagina,
                        HttpMethod.GET, entity, ListaClienteResponse.class);

        assertNotNull(response.getBody());

    }

}
