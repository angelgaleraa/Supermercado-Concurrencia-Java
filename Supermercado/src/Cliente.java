public class Cliente extends Thread{
    public String nombre;
    public String apellido;
    public double dinero;
    public int cantidadCompra;
    public Articulo articuloDeseado;
    public Supermercado supermercado;

    public Cliente(String nombre, String apellido, double dinero, int cantidadCompra, Articulo articuloDeseado, Supermercado supermercado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dinero = dinero;
        this.cantidadCompra = cantidadCompra;
        this.articuloDeseado = articuloDeseado;
        this.supermercado = supermercado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public Articulo getArticuloDeseado() {
        return articuloDeseado;
    }

    public void setArticuloDeseado(Articulo articuloDeseado) {
        this.articuloDeseado = articuloDeseado;
    }

    public Supermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    public void comprar() throws InterruptedException {
        getSupermercado().gestionarCompra(this);
    }

    public void pagar(double cantidad){
        setDinero(getDinero() - cantidad);
    }

    @Override
    public void run() {
        try {
            comprar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
