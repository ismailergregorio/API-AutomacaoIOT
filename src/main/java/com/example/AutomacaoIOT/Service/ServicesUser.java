package com.example.AutomacaoIOT.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.Config.Security.SecurityConfiguration;
import com.example.AutomacaoIOT.DTO.User.DTOGetUser;
import com.example.AutomacaoIOT.DTO.User.DTOPostUser;
import com.example.AutomacaoIOT.DTO.User.DTOUpdateUser;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicesUser {

    private final SecurityConfiguration securityConfiguration;

    private final RepositoryUser repositoriesUser;

    public ModelUser crearUser(DTOPostUser dados) {
        ModelUser usuario = ModelUser.builder()
                .nome(dados.nome())
                .sobrenome(dados.sobrenome())
                .email(dados.email())
                .senha(securityConfiguration.passwordEncoder().encode(dados.senha()))
                .telefone(dados.telefone())
                .role(dados.role())
                .build();

        repositoriesUser.save(usuario);

        return usuario;

    }

    public List<DTOGetUser> getListaUser() {
        try {
            return repositoriesUser.findAll()
                    .stream()
                    .map(u -> new DTOGetUser(
                            u.getId(),
                            u.getEmail(),
                            u.getNome(),
                            u.getSobrenome(),
                            u.getStatus(),
                            u.getTelefone(),
                            u.getRole().getRole()))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar lista de usuários", e);
        }
    }

    public ModelUser updateUser(Long id, DTOUpdateUser dados) {
        try {

            ModelUser usuario = repositoriesUser.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            usuario.setNome(dados.nome());
            usuario.setSobrenome(dados.sobrenome());
            usuario.setEmail(dados.email());
            usuario.setTelefone(dados.telefone());
            usuario.setRole(dados.role());
            usuario.setStatus(dados.status());

            repositoriesUser.save(usuario);

            return usuario;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    public void deleteUser(Long id) {
        try {

            Optional<ModelUser> usuario = repositoriesUser.findById(id);

            if (!usuario.isPresent()) {
                throw new RuntimeException("Usuário não encontrado.");
            }

            repositoriesUser.delete(usuario.get());

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
