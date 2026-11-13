
package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaProductoDtoRequest(

        @NotBlank(message = "La categoría no puede estar vacía")
        @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
        String tipoCategoria

) {}
