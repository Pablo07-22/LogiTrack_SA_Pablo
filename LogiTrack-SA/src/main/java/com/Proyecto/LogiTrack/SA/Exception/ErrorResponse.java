package com.Proyecto.LogiTrack.SA.Exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        String errorCode
) {
}