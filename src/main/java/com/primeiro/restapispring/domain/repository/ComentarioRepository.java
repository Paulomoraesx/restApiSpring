package com.primeiro.restapispring.domain.repository;

import com.primeiro.restapispring.domain.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
