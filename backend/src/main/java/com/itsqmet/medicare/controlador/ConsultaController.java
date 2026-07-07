package com.itsqmet.medicare.controlador;

import com.itsqmet.medicare.modelo.Consulta;
import com.itsqmet.medicare.repositorio.ConsultaRepository;
import com.itsqmet.medicare.repositorio.MedicoRepository;
import com.itsqmet.medicare.repositorio.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaController(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
                              PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping
    public List<Consulta> listar() {
        return consultaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestParam Long medicoId, @RequestParam Long pacienteId,
                                   @RequestBody Consulta datos) {
        var medico = medicoRepository.findById(medicoId).orElse(null);
        var paciente = pacienteRepository.findById(pacienteId).orElse(null);
        if (medico == null || paciente == null) {
            return ResponseEntity.badRequest().body("Medico o paciente no encontrado");
        }
        datos.setId(null);
        datos.setMedico(medico);
        datos.setPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaRepository.save(datos));
    }
}
