package cl.dsy1102.fonda.dao;

import cl.dsy1102.fonda.model.Bebida;
import java.util.List;

public interface BebidaDao {
    List<Bebida> cargar() throws PersistenciaException;
    void guardar(List<Bebida> bebidas) throws PersistenciaException;
}

