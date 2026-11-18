package com.Proyecto.LogiTrack.SA.Controller;

import com.Proyecto.LogiTrack.SA.Service.CiudadService;
import com.Proyecto.LogiTrack.SA.Dto.Request.CiudadDtoRequest;
import com.Proyecto.LogiTrack.SA.Dto.Response.CiudadDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudad")
@RequiredArgsConstructor
@Tag(name = "Ciudades", description = "Gestión de ciudades")
public class CiudadController {

    private final CiudadService ciudadService;

    @PostMapping
    @Operation(summary = "Registrar ciudad", description = "Registra una nueva ciudad.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ciudad creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CiudadDtoResponse> guardar(@Valid @RequestBody CiudadDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadService.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar ciudades", description = "Obtiene todas las ciudades registradas.")
    @ApiResponse(responseCode = "200", description = "Ciudades obtenidas correctamente")
    public ResponseEntity<List<CiudadDtoResponse>> listar() {
        return ResponseEntity.ok(ciudadService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ciudad por ID", description = "Consulta una ciudad mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ciudad encontrada"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<CiudadDtoResponse> buscar(@Parameter(description = "ID de la ciudad", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(ciudadService.buscarId(id));
    }

    @GetMapping("/nombre")
    @Operation(summary = "Buscar ciudad por nombre", description = "Busca una ciudad utilizando su nombre.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    public ResponseEntity<CiudadDtoResponse> buscarNombre(@Parameter(description = "Nombre de la ciudad", example = "Medellín") @RequestParam String nombre) {
        return ResponseEntity.ok(ciudadService.buscarNombre(nombre));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ciudad", description = "Modifica el nombre de una ciudad existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ciudad actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<CiudadDtoResponse> actualizar(@Valid @RequestBody CiudadDtoRequest dto, @Parameter(description = "ID de la ciudad que se actualizará", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(ciudadService.actualizar(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ciudad", description = "Elimina una ciudad mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ciudad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la ciudad", example = "1") @PathVariable Long id) {
        ciudadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
