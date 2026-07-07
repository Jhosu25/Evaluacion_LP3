package com.itsqmet.medicare.controlador;

import com.itsqmet.medicare.modelo.Medico;
import com.itsqmet.medicare.repositorio.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @GetMapping
    public List<Medico> listar() {
        return medicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtener(@PathVariable Long id) {
        return medicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medico> crear(@Valid @RequestBody Medico medico) {
        medico.setId(null);
        Medico guardado = medicoRepository.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable Long id, @Valid @RequestBody Medico datos) {
        return medicoRepository.findById(id).map(m -> {
            m.setNombre(datos.getNombre());
            if (datos.getPerfil() != null && m.getPerfil() != null) {
                m.getPerfil().setEspecialidad(datos.getPerfil().getEspecialidad());
                m.getPerfil().setTelefono(datos.getPerfil().getTelefono());
                m.getPerfil().setCorreo(datos.getPerfil().getCorreo());
            } else {
                m.setPerfil(datos.getPerfil());
            }
            return ResponseEntity.ok(medicoRepository.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!medicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/especialidad/{especialidad}")
    public List<Medico> porEspecialidad(@PathVariable String especialidad) {
        return medicoRepository.findByPerfil_EspecialidadIgnoreCase(especialidad);
    }
}
