package com.itsqmet.medicare.config;

import com.itsqmet.medicare.modelo.*;
import com.itsqmet.medicare.repositorio.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository, ConsultaRepository consultaRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("admin", passwordEncoder.encode("admin123"), "ADMIN"));
            usuarioRepository.save(new Usuario("user", passwordEncoder.encode("user123"), "USER"));
        }

        if (medicoRepository.count() == 0) {
            Medico m = new Medico();
            m.setNombre("Dra. Elena Ruiz");
            Perfil p = new Perfil();
            p.setEspecialidad("Cardiologia");
            p.setTelefono("0991234567");
            p.setCorreo("elena@medicare.com");
            m.setPerfil(p);
            medicoRepository.save(m);

            Paciente pac = new Paciente();
            pac.setNombre("Carlos Mora");
            pac.setEdad(40);
            pacienteRepository.save(pac);

            Consulta c = new Consulta();
            c.setFecha(LocalDate.now());
            c.setMotivo("Chequeo general");
            c.setMedico(m);
            c.setPaciente(pac);
            consultaRepository.save(c);
        }
    }
}
