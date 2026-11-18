package com.Proyecto.LogiTrack.SA.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdministradorDtoRequest(

        @NotBlank(message = "El username no puede estar vacío")
        @Size(max = 50, message = "El username no puede superar los 50 caracteres")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, max = 100,
                message = "La contraseña debe tener entre 8 y 100 caracteres")
        String password

) {}
