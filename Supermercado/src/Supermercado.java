import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Supermercado {
    public ArrayList<Articulo> listaArticulos;
    public Semaphore numClientesComprando;
    public Semaphore numReponedoresTrabajando;
    public int comprasRealizadas;
    public int comprasFallidas;
    public int numReposiciones;
    public Semaphore numClientesEntrando;

    public Supermercado(int numClientesComprando, int numReponedoresTrabajando, int numClientesEntrando) {
        this.listaArticulos = new ArrayList<>();
        this.numClientesComprando = new Semaphore(numClientesComprando);
        this.numReponedoresTrabajando = new Semaphore(numReponedoresTrabajando);
        comprasRealizadas = 0;
        comprasFallidas = 0;
        numReposiciones = 0;
        this.numClientesEntrando = new Semaphore(numClientesEntrando);
    }

    public ArrayList<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(ArrayList<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public Semaphore getNumClientesComprando() {
        return numClientesComprando;
    }

    public void setNumClientesComprando(Semaphore numClientesComprando) {
        this.numClientesComprando = numClientesComprando;
    }

    public Semaphore getNumReponedoresTrabajando() {
        return numReponedoresTrabajando;
    }

    public void setNumReponedoresTrabajando(Semaphore numReponedoresTrabajando) {
        this.numReponedoresTrabajando = numReponedoresTrabajando;
    }

    public int getComprasRealizadas() {
        return comprasRealizadas;
    }

    public void setComprasRealizadas(int comprasRealizadas) {
        this.comprasRealizadas = comprasRealizadas;
    }

    public int getComprasFallidas() {
        return comprasFallidas;
    }

    public void setComprasFallidas(int comprasFallidas) {
        this.comprasFallidas = comprasFallidas;
    }

    public int getNumReposiciones() {
        return numReposiciones;
    }

    public void setNumReposiciones(int numReposiciones) {
        this.numReposiciones = numReposiciones;
    }

    public Semaphore getNumClientesEntrando() {
        return numClientesEntrando;
    }

    public void setNumClientesEntrando(Semaphore numClientesEntrando) {
        this.numClientesEntrando = numClientesEntrando;
    }

    public void añadirArticulo(Articulo articulo){
        listaArticulos.add(articulo);
    }

    public void gestionarCompra(Cliente cliente) throws InterruptedException {
        getNumClientesEntrando().acquire();
        try{
            getNumClientesComprando().acquire();
            try{
                cliente.getArticuloDeseado().getSemComprar().acquire();
                try{
                    double total = cliente.getArticuloDeseado().getPrecio() * cliente.getCantidadCompra();
                    System.out.println(cliente.getNombre() + " intenta comprar "
                            + cliente.getCantidadCompra() + " uds de "
                            + cliente.getArticuloDeseado().getNombre() + ". El stock es: " + cliente.getArticuloDeseado().getStock());
                    Thread.sleep(200);
                    if (cliente.getDinero() < total){
                        System.err.println(cliente.getNombre() + " no tiene dinero suficiente porque tiene " + cliente.getDinero() +
                                " y la compra tiene un precio de " + total);
                        setComprasFallidas(getComprasFallidas() + 1);
                        return;
                    }
                    if (cliente.getArticuloDeseado().getStock() < cliente.getCantidadCompra() || cliente.getArticuloDeseado().getStock() == 0) {
                        System.out.println(cliente.getNombre() + " está esperando reposición de "
                                + cliente.getArticuloDeseado().getNombre());
                    }
                    cliente.getArticuloDeseado().descontarStock(cliente.getCantidadCompra());
                    cliente.pagar(total);
                    setComprasRealizadas(getComprasRealizadas() + 1);
                    System.out.println(cliente.getNombre() + " ha comprado "
                            + cliente.getCantidadCompra() + " uds de "
                            + cliente.getArticuloDeseado().getNombre()
                            + ". Stock ahora: " + cliente.getArticuloDeseado().getStock());
                } finally {
                    cliente.getArticuloDeseado().getSemComprar().release();
                }
            } finally {
                getNumClientesComprando().release();
            }
        } finally {
            getNumClientesEntrando().release();
        }
    }

    public void gestionarReposicion(Reponedor reponedor) throws InterruptedException {
        getNumReponedoresTrabajando().acquire();
        try{
            reponedor.getArticulo().getSemReponer().acquire();
            try {
                reponedor.getArticulo().incrementarStock(reponedor.getCantidadReponer());
                System.out.println(reponedor.getNombre() + " ha repuesto " + reponedor.getCantidadReponer() +
                        " uds de " + reponedor.getArticulo().getNombre() + ". Stock ahora: " + reponedor.getArticulo().getStock());
                setNumReposiciones(getNumReposiciones() + 1);
            } finally {
                reponedor.getArticulo().getSemReponer().release();
            }
        } finally {
            getNumReponedoresTrabajando().release();
        }
    }
}
