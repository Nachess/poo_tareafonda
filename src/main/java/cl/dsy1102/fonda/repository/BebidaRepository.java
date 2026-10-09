package cl.dsy1102.fonda.repository;

import cl.dsy1102.fonda.dao.BebidaDao;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.model.Bebida;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BebidaRepository implements Repository<Bebida> {

    private final BebidaDao dao;
    private final ObservableList<Bebida> bebidas;

    public BebidaRepository(BebidaDao dao) {
        this.dao = dao;
        this.bebidas = FXCollections.observableArrayList();
    }

    @Override
    public void cargar() throws PersistenciaException {
        List<Bebida> cargadas = dao.cargar();
        bebidas.setAll(cargadas);
    }

    @Override
    public ObservableList<Bebida> listar() {
        return bebidas;
    }

    @Override
    public void agregar(Bebida bebida) throws PersistenciaException {
        bebidas.add(bebida);
        dao.guardar(bebidas);
    }

    @Override
    public void actualizar(Bebida original, Bebida actualizado) throws PersistenciaException {
        int indice = bebidas.indexOf(original);
        if (indice != -1) {
            bebidas.set(indice, actualizado);
            dao.guardar(bebidas);
        }
    }

    @Override
    public void eliminar(Bebida bebida) throws PersistenciaException {
        bebidas.remove(bebida);
        dao.guardar(bebidas);
    }
}