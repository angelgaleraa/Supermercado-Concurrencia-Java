public class Reponedor extends Thread{
    public String nombre;
    public Articulo articulo;
    public int cantidadReponer;
    public Supermercado supermercado;

    public Reponedor(String nombre, Articulo articulo, int cantidadReponer, Supermercado supermercado) {
        this.nombre = nombre;
        this.articulo = articulo;
        this.cantidadReponer = cantidadReponer;
        this.supermercado = supermercado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidadReponer() {
        return cantidadReponer;
    }

    public void setCantidadReponer(int cantidadReponer) {
        this.cantidadReponer = cantidadReponer;
    }

    public Supermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    public void reponer() throws InterruptedException {
        getSupermercado().gestionarReposicion(this);
    }

    @Override
    public void run() {
        try{
            reponer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
