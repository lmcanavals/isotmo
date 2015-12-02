package pucp.isotesting.semanticsearch;

import java.io.IOException;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;
import java.util.Scanner;

/**
 *
 * @author n221
 */
public class BuscadorSemantico {

    private Indexador indexador = new Indexador();
    private Buscador buscador = new Buscador();
    private ISOTMOntology isotmo = new ISOTMOntology();

    private void  menu() {
        System.out.println("1. Indexar el asunto");
        System.out.println("2. Consulta de texto");
        System.out.println("3. Consulta semantica");
        System.out.println("\n0. Salir");
    }

    public void go(String[] args) throws IOException, ParseException {
        String op;
        Scanner in = new Scanner(System.in);
        buscador.crearBuscador();
        do {
            menu();
            op = in.nextLine();
            switch (op.charAt(0)) {
                case '0': System.out.println(" --- Bye bye! --- "); break;
                case '1':
                    indexador.indexarDocumentosEjemplo();
                    break;
                case '2':
                    System.out.println("Ingrese el texto: ");
                    String texto = in.nextLine();
                    buscador.buscarDocumentos(texto);
                    break;
                case '3':
                    System.out.println("Ingrese la clase: ");
                    String uri = Util.NS + in.nextLine();
                    String deClase = expandirConsulta(isotmo, uri);
                    buscador.buscarDocumentosPorClase(deClase);
                    break;
                default: System.out.println(" --- Opción incorrecta --- ");
            }
        } while (op.charAt(0) != '0');
        buscador.cerrarBuscador();
    }

    public static void main(String[] args) throws IOException, ParseException {
        new BuscadorSemantico().go(args);
    }

    //private static String expandirConsulta(PizzaOntology po, String uri) {
    private String expandirConsulta(ISOTMOntology po, String uri) {
        String deClases = "";
        List<String> listaClases = po.obtenerSubClases(uri);
        for (String nmClase : listaClases) {
            if (deClases.isEmpty()) {
                deClases = nmClase;
            } else {
                deClases += " OR " + nmClase;
            }
        }
        return deClases;
    }
}
