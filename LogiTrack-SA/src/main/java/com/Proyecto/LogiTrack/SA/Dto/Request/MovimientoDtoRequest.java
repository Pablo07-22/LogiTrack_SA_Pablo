package com.Proyecto.LogiTrack.SA.Dto.Request;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MovimientoDtoRequest(

        @NotNull(message = "El tipo de movimiento no puede estar nulo")
        Movimiento.TipoMovimiento tipoMovimiento,

        @NotNull(message = "El usuario responsable no puede estar nulo")
        @Positive(message = "El ID del usuario debe ser positivo")
        Long usuarioResponsableId,

        @Positive(message = "El ID de la bodega de origen debe ser positivo")
        Long bodegaOrigenId,

        @Positive(message = "El ID de la bodega de destino debe ser positivo")
        Long bodegaDestinoId,

        @Size(max = 500, message = "La observación no puede superar los 500 caracteres")
        String observacion,

        @NotNull(message = "La lista de detalles no puede estar nula")
        @Size(min = 1, message = "El movimiento debe contener al menos un producto")
        List<DetalleMovimientoDtoRequest> detalles

) {}
