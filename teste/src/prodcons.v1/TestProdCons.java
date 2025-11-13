package prodcons.v1;

import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class TestProdCons {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties(); // tabela de propriedades [web:4]
        try (InputStream in = TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"))
        {
            System.out.println(System.getProperty("java.class.path"));
            if (in == null) {
                throw new IllegalStateException("options.xml não encontrado no classpath");
            }
            properties.loadFromXML(in); // carrega propriedades a partir de XML [web:4][web:10]
        }

        int nProd = Integer.parseInt(properties.getProperty("nProd")); // converte para int [web:4][web:16]
        int nCons = Integer.parseInt(properties.getProperty("nCons")); // converte para int [web:4][web:16]
        int bufSz = Integer.parseInt(properties.getProperty("bufSz")); // converte para int [web:4][web:16]
        int prodTime = Integer.parseInt(properties.getProperty("prodTime")); // converte para int [web:4][web:16]
        int consTime = Integer.parseInt(properties.getProperty("consTime")); // converte para int [web:4][web:16]
        int minProd = Integer.parseInt(properties.getProperty("minProd")); // converte para int [web:4][web:16]
        int maxProd = Integer.parseInt(properties.getProperty("maxProd")); // converte para int [web:4][web:16]


        System.out.println("nProd = " + nProd);
        System.out.println("nCons = " + nCons);
        System.out.println("bufSz = " + bufSz);
        System.out.println("prodTime = " + prodTime);
        System.out.println("consTime = " + consTime);
        System.out.println("minProd = " + minProd);
        System.out.println("maxProd = " + maxProd);

        /*
        buffer -> tableau taille fixe
        simular tratamento de msg com sleep para o consommateur -> durée moyenne = consTime
        producteur -> nombre aléatoire de messages entre minProd et maxProd
        dure moyenne pour produire -> prodTime
        1 production a la fois e 1 consommation a la fois pour chaque producteur/consommateur

         */

        ProdConsBuffer buffer = new ProdConsBuffer(bufSz);
        Random random = new Random();

        for(int i = 0; i < nProd; i++){
            Producer p = new Producer(random.nextInt((maxProd - minProd) + 1) + minProd, buffer, prodTime);
            p.start();
        }
        for(int j = 0; j < nCons; j++){
            Consumer c = new Consumer(buffer, consTime);
            c.start();
        }
        //create n producers
        //create m consumers


    }
}
