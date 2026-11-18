package com.Proyecto.LogiTrack.SA.Dto.Request;

import com.Proyecto.LogiTrack.SA.Model.Movimiento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(name = "MovimientoDtoRequest", description = "Datos de un movimiento de entrada, salida o transferencia de inventario.")
public record MovimientoDtoRequest(

        @Schema(description = "Tipo de movimiento que se desea registrar", example = "ENTRADA", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El tipo de movimiento no puede estar nulo")
        Movimiento.TipoMovimiento tipoMovimiento,

        @Schema(description = "Identificador del usuario responsable del movimiento", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El usuario responsable no puede estar nulo")
        @Positive(message = "El ID del usuario debe ser positivo")
        Long usuarioResponsableId,

        @Schema(description = "Identificador de la bodega de origen. Se utiliza en salidas y transferencias", example = "1")
        @Positive(message = "El ID de la bodega de origen debe ser positivo")
        Long bodegaOrigenId,

        @Schema(description = "Identificador de la bodega de destino. Se utiliza en entradas y transferencias", example = "2")
        @Positive(message = "El ID de la bodega de destino debe ser positivo")
        Long bodegaDestinoId,

        @Schema(description = "Observación adicional del movimiento", example = "Ingreso de mercancía del proveedor", maxLength = 500)
        @Size(max = 500, message = "La observación no puede superar los 500 caracteres")
        String observacion,

        @Schema(description = "Lista de productos y cantidades que conforman el movimiento", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "La lista de detalles no puede estar nula")
        @Size(min = 1, message = "El movimiento debe contener al menos un producto")
        @Valid
        List<DetalleMovimientoDtoRequest> detalles

) {}
