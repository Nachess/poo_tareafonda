package cl_dsy1102_fonda;

public abstract class Bebida {
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
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío ni ser nulo");
        }
        this.nombre = nombre;
    }

    public int getVolumenML() {
        return volumenML;
    }

    public void setVolumenML(int volumenML) {
        if (volumenML < 100 || volumenML > 3000){
            throw new IllegalArgumentException("Debe encontrarse en el rango entre 100 y 3.000 mililitros");
        }
        this.volumenML = volumenML;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock <= 0){
            throw new IllegalArgumentException("Debe se un numero mayor que 0");
        }
        this.stock = stock;
    }
    public abstract double calcularPrecio();
    public abstract String obtenerDetalle();

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Volumen: " + volumenML + " ml";
    }
}

