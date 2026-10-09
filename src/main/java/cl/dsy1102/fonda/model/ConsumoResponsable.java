package cl.dsy1102.fonda.model;

public interface ConsumoResponsable {

    boolean tieneVentaRestringida();

    void restringirVenta();

    boolean superaLimite(int unidades);

    boolean esAptoParaConsumo(int edad);
}
