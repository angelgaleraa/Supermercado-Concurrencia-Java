import java.util.concurrent.Semaphore;

public class Articulo {
    public String nombre;
    public int stock;
    public double precio;
    public Semaphore semComprar;
    public Semaphore semReponer;

    public Articulo(String nombre, int stock, double precio, int semComprar, int semReponer) {
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.semComprar = new Semaphore(semComprar);
        this.semReponer = new Semaphore(semReponer);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Semaphore getSemComprar() {
        return semComprar;
    }

    public void setSemComprar(Semaphore semComprar) {
        this.semComprar = semComprar;
    }

    public Semaphore getSemReponer() {
        return semReponer;
    }

    public void setSemReponer(Semaphore semReponer) {
        this.semReponer = semReponer;
    }

    public synchronized void incrementarStock(int cantidad){
        setStock(getStock() + cantidad);
        notifyAll();
    }

    public synchronized void descontarStock(int cantidad) throws InterruptedException {
        while (cantidad > getStock()){
            wait();
        }
        setStock(getStock() - cantidad);
    }
}
