package cl.dsy1102.fonda.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BebidaAlcoholica.class, name = "ALCOHOLICA"),
        @JsonSubTypes.Type(value = BebidaSinAlcohol.class, name = "SIN_ALCOHOL")
})

public abstract class Bebida implements ConsumoResponsable {

    private String nombre;
    private int volumenML;
    private int stock;

    public Bebida() {

    }

    public Bebida(String nombre, int volumenML, int stock) {
        setNombre(nombre);
        setVolumenML(volumenML);
        setStock(stock);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacio.");
        }
        this.nombre = nombre.trim();
    }

    public int getVolumenML() {
        return volumenML;
    }

    public void setVolumenML(int volumenML) {
        if (volumenML < 100 || volumenML > 3000) {
            throw new IllegalArgumentException("El volumen debe estar entre 100 y 3000 ml.");
        }
        this.volumenML = volumenML;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock debe ser mayor que cero.");
        }
        this.stock = stock;
    }

    public abstract String obtenerTipo();

    public abstract double calcularPrecio();

    public abstract String obtenerDetalle();

    @Override
    public abstract boolean esAptoParaConsumo(int edad);

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Volumen: " + volumenML + " ml";
    }
}