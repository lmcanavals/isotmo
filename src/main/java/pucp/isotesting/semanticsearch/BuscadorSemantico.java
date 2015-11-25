/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.isotesting.semanticsearch;

import java.io.IOException;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author n221
 */
public class BuscadorSemantico {
    public static void main(String[] args) throws IOException, ParseException {
        Indexador indexador = new Indexador();
        Buscador buscador = new Buscador();
        //PizzaOntology po = new PizzaOntology();
        ISOTMOntology isotmo = new ISOTMOntology();
        indexador.indexarDocumentosEjemplo();
       
        // para obtener la URI se sugiere hacer alguna UI interactiva para seleccionarla.
        String texto = "vegetable";
        String uri = Util.NS + "Practice";
        
        String deClase = expandirConsulta(isotmo, uri);
        buscador.crearBuscador();
        System.out.println("==>> Buscador textual");
        buscador.buscarDocumentos(texto);
        System.out.println("==>> Buscador semantico");
        buscador.buscarDocumentosPorClase(deClase);
        buscador.cerrarBuscador();
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
