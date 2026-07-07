package com.itsqmet.medicare.repositorio;

import com.itsqmet.medicare.modelo.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {


    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
}
