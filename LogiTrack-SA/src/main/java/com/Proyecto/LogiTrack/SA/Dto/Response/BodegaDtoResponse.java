package com.Proyecto.LogiTrack.SA.Dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "BodegaDtoResponse", description = "Informacion de una bodega registrada en el sistema.")
public record BodegaDtoResponse(

        @Schema(description = "Identificador de la bodega", example = "1")
        Long id,

        @Schema(description = "Nombre de la bodega", example = "Bodega Principal")
        String nombre,

        @Schema(description = "Ciudad donde se encuentra ubicada la bodega")
        CiudadDtoResponse ciudad,

        @Schema(description = "Usuario encargado de la bodega")
        UsuarioResumenDtoResponse encargado,

        @Schema(description = "Capacidad de almacenamiento", example = "1500.00")
        BigDecimal capacidad,

        @Schema(description = "Fecha de creación de la bodega", example = "2026-11-17T10:30:00")
        LocalDateTime fechaCreacion

) {}
