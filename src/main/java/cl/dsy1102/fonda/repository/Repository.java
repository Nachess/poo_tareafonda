package cl.dsy1102.fonda.repository;

import cl.dsy1102.fonda.dao.PersistenciaException;
import javafx.collections.ObservableList;

public interface Repository<T> {
    void cargar() throws PersistenciaException;
    ObservableList<T> listar();
    void agregar(T entidad) throws PersistenciaException;
    void actualizar(T original, T actualizado) throws PersistenciaException;
    void eliminar(T entidad) throws PersistenciaException;
}