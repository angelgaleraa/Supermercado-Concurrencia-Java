public class MainClientes {
    public static void main(String[] args) throws InterruptedException {

        // Creo un supermercado con las mismas restricciones que en el principal.
        Supermercado supermercado = new Supermercado(3, 2, 6);

        // En este caso solo utilizo un artículo, con muy poco stock,
        // para que todos los clientes compitan por él.
        Articulo pan = new Articulo("Pan", 1, 1.00, 1, 1);
        supermercado.añadirArticulo(pan);

        // Creo varios clientes que intentarán comprar pan simultáneamente.
        Cliente c1 = new Cliente("Ángel", "Galera", 10, 2, pan, supermercado);
        Cliente c2 = new Cliente("Lucía", "Santos", 10, 3, pan, supermercado);
        Cliente c3 = new Cliente("Mario", "López", 10, 2, pan, supermercado);
        Cliente c4 = new Cliente("Carmen", "Vera", 5, 2, pan, supermercado);
        Cliente c5 = new Cliente("Diego", "Navarro", 7, 2, pan, supermercado);

        // Inicio los clientes en paralelo.
        c1.start(); c2.start(); c3.start(); c4.start(); c5.start();

        // Pausa para que agoten el stock y alguno se quede esperando.
        Thread.sleep(2000);

        // Lanzo dos reponedores para reponer pan.
        Reponedor r1 = new Reponedor("Carlos", pan, 10, supermercado);
        Reponedor r2 = new Reponedor("Julia", pan, 8, supermercado);

        r1.start();
        r2.start();

        // Espero a todos los hilos.
        c1.join(); c2.join(); c3.join(); c4.join(); c5.join();
        r1.join(); r2.join();

        // Muestro resultados.
        System.out.println("\n===== RESULTADO MAIN CLIENTES =====");
        System.out.println("Stock final de pan: " + pan.getStock());
        System.out.println("Compras realizadas: " + supermercado.getComprasRealizadas());
        System.out.println("Compras fallidas: " + supermercado.getComprasFallidas());
        System.out.println("Reposiciones realizadas: " + supermercado.getNumReposiciones());
    }
}



