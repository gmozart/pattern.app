package dio.digitalinovation.pattern.app.service.impl;


import dio.digitalinovation.pattern.app.model.Cliente;
import dio.digitalinovation.pattern.app.model.ClienteRepository;
import dio.digitalinovation.pattern.app.model.Endereco;
import dio.digitalinovation.pattern.app.model.EnderecoRepository;
import dio.digitalinovation.pattern.app.service.ClienteService;
import dio.digitalinovation.pattern.app.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository  enderecoRepository;

    @Autowired
    private ViaCepService cepService;

    @Override
    public Iterable<Cliente> buscarTodos() {

        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClientecomCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clientedb = clienteRepository.findById(id);
        if(clientedb.isPresent()){
            salvarClientecomCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
      clienteRepository.deleteById(id);
    }

    private void salvarClientecomCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() ->{
            Endereco novoEndereco = cepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);

            return novoEndereco;
        });
        
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

}
