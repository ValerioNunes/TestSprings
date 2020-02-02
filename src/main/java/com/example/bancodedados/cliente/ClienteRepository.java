package com.example.bancodedados.cliente;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findFirstByNome(String nome);

    @Query("SELECT u FROM Cliente u WHERE u.telefone = ?1")
    Cliente buscaPor(String telefone);

    @Query(value = "From Cliente")
    List<Cliente> todos(Sort sort);
}
