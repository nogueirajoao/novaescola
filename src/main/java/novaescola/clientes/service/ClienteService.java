package novaescola.clientes.service;

import novaescola.clientes.controller.ListaClienteResponse;
import novaescola.clientes.model.Cliente;
import novaescola.clientes.model.ClienteDTO;
import novaescola.clientes.model.repository.ClienteRepository;
import org.apache.tomcat.jni.Local;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ClienteDTO criarCliente(ClienteDTO dto) {

        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(String.class, LocalDate.class);
        mapper.addConverter(stringToLocalDate);
        Cliente cliente = mapper.map(dto, Cliente.class);

        return converterParaDTO(repository.save(cliente));
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO cliente) {

        Optional<Cliente> optional = repository.findById(id);

        if (optional.isPresent()) {
            Cliente entidade = optional.get();
            if (cliente.getNome() != null) {
                entidade.setNome(cliente.getNome());
            }
            if (cliente.getEmail() != null) {
                entidade.setEmail(cliente.getEmail());
            }
            if (cliente.getDataNascimento() != null) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.now();
                entidade.setDataNascimento(localDate.parse(cliente.getDataNascimento(), dateTimeFormatter));
            }
            return converterParaDTO(repository.save(entidade));
        } else {
            throw new EntityNotFoundException("Nenhuma entidade encontrada com o id: " + id);
        }
    }

    public void removerCliente(Long id) {
        repository.deleteById(id);
    }

    public ListaClienteResponse buscarTodos(Integer limite, Integer pagina) {
        Pageable pageable = PageRequest.of(pagina, limite);
        Page<Cliente> clientes = repository.findAll(pageable);
        List<ClienteDTO> dtos = new ArrayList<>();
        clientes.forEach(cliente -> {
            dtos.add(converterParaDTO(cliente));
        });

        return new ListaClienteResponse(clientes.getTotalElements(), dtos);
    }

    private Converter<String, LocalDate> stringToLocalDate = new AbstractConverter<String, LocalDate>() {
        @Override
        protected LocalDate convert(String s) {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            LocalDate data = localDate.parse(s, dateTimeFormatter);

            return data;
        }
    };

    private Converter<LocalDate, String> localDateToString = new AbstractConverter<LocalDate, String>() {
        @Override
        protected String convert(LocalDate localDate) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return localDate.format(dateTimeFormatter);
        }
    };

    public ClienteDTO converterParaDTO(Cliente cliente) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(LocalDate.class, String.class);
        mapper.addConverter(localDateToString);
        ClienteDTO dto = mapper.map(cliente, ClienteDTO.class);

        return dto;
    }

}
