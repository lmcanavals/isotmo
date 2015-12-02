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
        char op;
        Scanner in = new Scanner(System.in);
        do {
            menu();
            op = in.next(".").charAt(0);
            switch (op) {
                case '0': System.out.println(" --- Bye bye! --- "); break;
                case '1':
                    indexador.indexarDocumentosEjemplo();
                    break;
                case '2':
                    String texto = in.nextLine();
                    buscador.crearBuscador();
                    buscador.buscarDocumentos(texto);
                    buscador.cerrarBuscador();
                    break;
                case '3':
                    String uri = Util.NS + in.nextLine();
                    String deClase = expandirConsulta(isotmo, uri);
                    buscador.crearBuscador();
                    buscador.buscarDocumentosPorClase(deClase);
                    buscador.cerrarBuscador();
                    break;
                default: System.out.println(" --- Opción incorrecta --- ");
            }
        } while (op != '0');
    }

    public static void main(String[] args) throws IOException, ParseException {
        new BuscadorSemantico().go(args);
    }

    //private static String expandirConsulta(PizzaOntology po, String uri) {
    private static String expandirConsulta(ISOTMOntology po, String uri) {
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
