package cl.dsy1102.fonda.dao;

import cl.dsy1102.fonda.model.Bebida;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonBebidaDao implements BebidaDao {

    private final File archivo;
    private final ObjectMapper mapper;

    public JsonBebidaDao(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        this.mapper = new ObjectMapper();
        // Formatea el JSON para que sea legible (pretty print) como pide R6
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public List<Bebida> cargar() throws PersistenciaException {
        // R6: Si el archivo no existe (primera ejecución), retorna lista vacía
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try {
            return mapper.readValue(archivo, new TypeReference<List<Bebida>>() {});
        } catch (IOException e) {
            // R6/R7: Si está dañado o no se puede leer, lanza PersistenciaException
            throw new PersistenciaException("Error al leer el archivo JSON o datos dañados.", e);
        }
    }

    @Override
    public void guardar(List<Bebida> bebidas) throws PersistenciaException {
        try {
            // R6: Si la carpeta contenedora (ej: data/) no existe, la crea
            File carpetaPadre = archivo.getParentFile();
            if (carpetaPadre != null && !carpetaPadre.exists()) {
                carpetaPadre.mkdirs();
            }

            mapper.writeValue(archivo, bebidas);
        } catch (IOException e) {
            // R6/R7: Si falla la escritura en disco
            throw new PersistenciaException("No se pudieron guardar los datos en el disco.", e);
        }
    }
}