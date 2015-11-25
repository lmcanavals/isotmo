/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.isotesting.semanticsearch;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author n221
 */
public class Buscador {
    private final String nmCarpetaIndice = Util.INDEXFLDR;
    private Directory directorio;
    private DirectoryReader directorioLectura;
    private StandardAnalyzer analizador;
    private IndexSearcher buscador;
    
    public void crearBuscador() throws IOException {
        directorio = FSDirectory.open(new File(nmCarpetaIndice));
        directorioLectura = DirectoryReader.open(directorio);
        directorio.close();
        buscador = new IndexSearcher(directorioLectura);
        analizador = new StandardAnalyzer();
    }
    
    public void cerrarBuscador() throws IOException {
        directorioLectura.close();
    }
    
    public ScoreDoc[] buscarDocumentos(String vlABuscar) throws IOException, ParseException {
        return buscar(vlABuscar, "texto");
    }
    
    public ScoreDoc[] buscarDocumentosPorClase(String vlABuscar) throws IOException, ParseException {
        return buscar(vlABuscar, "clase");
    }
    
    public ScoreDoc[] buscar(String vlABuscar, String campo) throws ParseException, IOException {
        ScoreDoc[] docs;
        QueryParser parser = new QueryParser(campo, analizador);
        Query query = parser.parse(vlABuscar);
        
        docs = buscador.search(query, 100).scoreDocs;
        visualizarStuff(docs);
        return docs;
    }

    private void visualizarStuff(ScoreDoc[] docs) {
        for (ScoreDoc doc : docs) {
            System.out.println("Doc: " + doc);
        }
    }
}
