package cl.dsy1102.fonda.model;

public class BebidaSinAlcohol extends Bebida {

    private static final double PRECIO_BASE = 2000;
    private static final double RECARGO_AZUCAR = 0.10;
    private static final int LIMITE_AZUCAR = 80;

    private int azucarPorLitro;

    public BebidaSinAlcohol() {
        super();
    }

    public BebidaSinAlcohol(String nombre, int volumenML, int stock, int azucarPorLitro) {
        super(nombre, volumenML, stock);
        setAzucarPorLitro(azucarPorLitro);
    }

    public int getAzucarPorLitro() {
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(int azucarPorLitro) {
        if (azucarPorLitro < 0) {
            throw new IllegalArgumentException("El azúcar por litro no puede ser negativo.");
        }
        this.azucarPorLitro = azucarPorLitro;
    }

    @Override
    public double calcularPrecio() {
        return azucarPorLitro > LIMITE_AZUCAR ? PRECIO_BASE * (1 + RECARGO_AZUCAR) : PRECIO_BASE;
    }

    @Override
    public String obtenerDetalle() {
        return "Tipo: Bebida Sin Alcohol | Nombre: " + getNombre()
                + " | Volumen: " + getVolumenML() + " ml"
                + " | Stock: " + getStock()
                + " | Azúcar: " + azucarPorLitro + " g/L"
                + " | Precio: $" + String.format("%.0f", calcularPrecio());
    }

    @Override
    public String obtenerTipo() {
        return "Sin alcohol";
    }

    @Override
    public boolean tieneVentaRestringida() {
        return false;
    }

    @Override
    public void restringirVenta() {
        // Las bebidas sin alcohol no restringen su venta
    }

    @Override
    public boolean superaLimite(int unidades) {
        return false;
    }

    @Override
    public boolean esAptoParaConsumo(int edad) {
        return true;
    }
}