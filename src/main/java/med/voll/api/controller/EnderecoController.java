package med.voll.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.medico.DadosCadastroMedico;

@RestController
@RequestMapping("/endereco2")
public class EnderecoController {

    @PostMapping
    public void cadastrarEndereco(@RequestBody DadosCadastroPaciente dados) {
        //System.out.println("Dados completos: " + dados);
        System.out.println("Endereço: " + dados.endereco());
    }

}