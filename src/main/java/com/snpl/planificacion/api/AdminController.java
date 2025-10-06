package com.snpl.planificacion.api;

import com.snpl.planificacion.mapper.UsuarioMapper;
import com.snpl.planificacion.model.dto.UsuarioDTO;
import com.snpl.planificacion.model.entity.Usuario;
import com.snpl.planificacion.model.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

// Bloquea el acceso a /admin/dashboard, permite el acceso a usuarios con el rol de administrados
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<String> obtenerAdminDashboard(){
        return ResponseEntity.ok("Bienvenido al panel de administrador");
    }

    private final UsuarioService usuarioService;
    private final ProyectoInversionService proyectoInversionService;
    private final PlanInstitucionalService planService;
    private final ObjetivosEstrategicosService objetivosEstratService;
    private final MetaService metaService;
    private final IntegracionService integracionService;
    private final IndicadorService indicadorService;
    private final HistorialCambiosService historialService;
    private final EventoAuditoriaService eventoService;

    public AdminController(
            UsuarioService usuarioService,
            ProyectoInversionService proyectoInversionService,
            PlanInstitucionalService planService,
            ObjetivosEstrategicosService objetivosEstratService,
            MetaService metaService,
            IntegracionService integracionService,
            IndicadorService indicadorService,
            HistorialCambiosService historialService,
            EventoAuditoriaService eventoService
    ) {
        this.usuarioService = usuarioService;
        this.proyectoInversionService = proyectoInversionService;
        this.planService = planService;
        this.objetivosEstratService = objetivosEstratService;
        this.metaService = metaService;
        this.integracionService = integracionService;
        this.indicadorService = indicadorService;
        this.historialService = historialService;
        this.eventoService = eventoService;
    }
    // ============= USUARIOS ==============
    @GetMapping("/usuarios")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuariosDTO();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuario) {
        ResponseEntity.ok(usuarioService.crearUsuarioDTO(usuario));
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id,
                                                        @RequestBody UsuarioDTO usuarioDTO) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(UsuarioMapper.toDTO(actualizado));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
