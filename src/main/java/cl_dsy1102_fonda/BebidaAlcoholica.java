package cl_dsy1102_fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable{
    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(String nombre, int volumenML, int stock, double gradosAlcohol, boolean certificada, boolean ventaRestringida) {
        super(nombre, volumenML, stock);
        setGradosAlcohol(gradosAlcohol);
        setCertificada(certificada);
        setVentaRestringida(ventaRestringida);
    }

    public BebidaAlcoholica(){

    }

    @Override
    public double calcularPrecio() {
        return 0;
    }

    @Override
    public String obtenerDetalle() {
        return "";
    }

    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {


        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    public boolean tieneVentaRestringida() {
        return ventaRestringida;
    }

    public void setVentaRestringida(boolean ventaRestringida) {
        this.ventaRestringida = ventaRestringida;
    }

    public void restringirVenta() {
        this.ventaRestringida = true;
    }

    public boolean superaLimite(int unidades) {
        return unidades > LIMITE_UNIDADES_POR_CLIENTE;
    }

}


