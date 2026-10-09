package cl.dsy1102.fonda.dao;

public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}