package com.Proyecto.LogiTrack.SA.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest(

        @NotBlank(message = "El username es obligatorio")
        @Size(min = 4, max = 50, message = "El username debe tener entre 4 y 50 caracteres")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 4, max = 100, message = "La contraseña debe tener entre 4 y 100 caracteres")
        String password

) {
}
