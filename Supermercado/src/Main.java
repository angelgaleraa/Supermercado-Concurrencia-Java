public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Primero creo el supermercado indicando las limitaciones de concurrencia:
        // 3 clientes pueden comprar a la vez, 2 reponedores pueden trabajar simultáneamente
        // y un máximo de 6 clientes pueden estar dentro del supermercado.
        Supermercado supermercado = new Supermercado(3, 2, 6);

        // Creo varios artículos con un stock inicial muy bajo para forzar esperas.
        Articulo manzanas = new Articulo("Manzanas", 2, 1.50, 1, 1);
        Articulo leche = new Articulo("Leche", 1, 0.90, 1, 1);
        Articulo huevos = new Articulo("Huevos", 3, 2.00, 1, 1);
        Articulo pan = new Articulo("Pan", 1, 1.10, 1, 1);

        // Los añado al supermercado.
        supermercado.añadirArticulo(manzanas);
        supermercado.añadirArticulo(leche);
        supermercado.añadirArticulo(huevos);
        supermercado.añadirArticulo(pan);

        // Creo varios clientes. La idea es que haya suficientes para que algunos se queden esperando.
        Cliente c1 = new Cliente("Ángel", "Galera", 10, 3, manzanas, supermercado);
        Cliente c2 = new Cliente("Lucía", "Santos", 5, 2, leche, supermercado);
        Cliente c3 = new Cliente("Mario", "López", 20, 5, huevos, supermercado);
        Cliente c4 = new Cliente("Adriana", "Miceli", 1, 2, manzanas, supermercado);
        Cliente c5 = new Cliente("Raúl", "Martínez", 8, 2, pan, supermercado);
        Cliente c6 = new Cliente("Sara", "Gómez", 15, 4, huevos, supermercado);
        Cliente c7 = new Cliente("Carlos", "Ruiz", 6, 3, pan, supermercado);
        Cliente c8 = new Cliente("Nuria", "Iglesias", 7, 3, manzanas, supermercado);

        // Inicio todos los clientes para que empiecen a comprar en paralelo.
        c1.start(); c2.start(); c3.start(); c4.start();
        c5.start(); c6.start(); c7.start(); c8.start();

        // Hago una pequeña pausa para dar tiempo a que algunos clientes agoten el stock
        // y se queden esperando reposición.
        Thread.sleep(2000);

        // Creo varios reponedores. Hay más de dos para demostrar que el límite global funciona:
        // solo pueden trabajar 2 al mismo tiempo.
        Reponedor r1 = new Reponedor("R1", manzanas, 5, supermercado);
        Reponedor r2 = new Reponedor("R2", huevos, 6, supermercado);
        Reponedor r3 = new Reponedor("R3", leche, 4, supermercado);
        Reponedor r4 = new Reponedor("R4", pan, 8, supermercado);

        // Inicio algunos reponedores primero…
        r1.start(); r2.start();

        // …y retraso los otros para visualizar bien la concurrencia.
        Thread.sleep(1500);
        r3.start();

        Thread.sleep(1500);
        r4.start();

        // Espero a que todos los clientes terminen.
        c1.join(); c2.join(); c3.join(); c4.join();
        c5.join(); c6.join(); c7.join(); c8.join();

        // Espero a que todos los reponedores terminen también.
        r1.join(); r2.join(); r3.join(); r4.join();

        // Finalmente muestro el informe con el estado final del supermercado.
        System.out.println("\n===== INFORME FINAL =====");
        for (Articulo a : supermercado.getListaArticulos()) {
            System.out.println(a.getNombre() + " → Stock final: " + a.getStock());
        }
        System.out.println("Compras realizadas: " + supermercado.getComprasRealizadas());
        System.out.println("Compras fallidas: " + supermercado.getComprasFallidas());
        System.out.println("Reposiciones realizadas: " + supermercado.getNumReposiciones());
    }
}


