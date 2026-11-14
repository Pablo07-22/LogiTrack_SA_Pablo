package com.Proyecto.LogiTrack.SA.Exception;

public class DatabaseRuleException extends RuntimeException {

    public DatabaseRuleException(String mensaje) {
        super(mensaje);
    }
}