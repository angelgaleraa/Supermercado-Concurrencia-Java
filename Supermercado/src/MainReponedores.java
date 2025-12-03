public class MainReponedores {
    public static void main(String[] args) throws InterruptedException {

        // Creo el supermercado con las limitaciones de concurrencia.
        Supermercado supermercado = new Supermercado(3, 2, 6);

        // Creo un artículo con stock 0 para obligar a que los clientes esperen desde el inicio.
        Articulo leche = new Articulo("Leche", 0, 1.20, 1, 1);
        supermercado.añadirArticulo(leche);

        // Dos clientes intentan comprar leche, pero no podrán hasta que algún reponedor reponga.
        Cliente c1 = new Cliente("Ángel", "Galera", 10, 1, leche, supermercado);
        Cliente c2 = new Cliente("Marina", "Torres", 8, 1, leche, supermercado);

        c1.start();
        c2.start();

        // Pequeña pausa para que ambos hilos lleguen al punto de espera.
        Thread.sleep(2000);

        // Creo varios reponedores. Hay 4, pero solo dos podrán trabajar a la vez,
        // gracias al semáforo global de reponedores.
        Reponedor r1 = new Reponedor("Carlos", leche, 3, supermercado);
        Reponedor r2 = new Reponedor("Sara", leche, 5, supermercado);
        Reponedor r3 = new Reponedor("Pedro", leche, 4, supermercado);
        Reponedor r4 = new Reponedor("Laura", leche, 6, supermercado);

        // Inicio los dos primeros y los otros dos con pequeñas pausas
        // para que se vea mejor la intercalación de hilos.
        r1.start();
        r2.start();

        Thread.sleep(500);
        r3.start();

        Thread.sleep(500);
        r4.start();

        // Espero a que terminen clientes y reponedores.
        c1.join(); c2.join();
        r1.join(); r2.join(); r3.join(); r4.join();

        // Imprimo el resultado final.
        System.out.println("\n===== RESULTADO MAIN REPONEDORES =====");
        System.out.println("Stock final de leche: " + leche.getStock());
        System.out.println("Compras realizadas: " + supermercado.getComprasRealizadas());
        System.out.println("Compras fallidas: " + supermercado.getComprasFallidas());
        System.out.println("Reposiciones realizadas: " + supermercado.getNumReposiciones());
    }
}



