package com.itsqmet.medicare.repositorio;

import com.itsqmet.medicare.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {


    List<Medico> findByPerfil_EspecialidadIgnoreCase(String especialidad);
}
