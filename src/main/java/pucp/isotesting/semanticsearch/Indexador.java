package pucp.isotesting.semanticsearch;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexador {

    private final String nmCarpetaIndice = Util.INDEXFLDR;
    private Version version;
    private Directory directorio;
    private StandardAnalyzer analizador;
    private IndexWriterConfig config;
    private IndexWriter escritor;

    public void crearEscritor() throws IOException {
        //directorio en donde se almacenará el índice
        directorio = FSDirectory.open(new File(nmCarpetaIndice));
        //directorio = new RAMDirectory();

        //clase usada para preparar el texto para la indexación
        //stem, lema, stoplist, tokenizador, etc...
        analizador = new StandardAnalyzer();

        version = Version.LUCENE_4_10_1;

        //configuración para crear el índice
        config = new IndexWriterConfig(version, analizador);

        //objeto usado para crear y mantener el índice
        escritor = new IndexWriter(directorio, config);
    }

    public void cerrarEscritor() throws IOException {
        escritor.close();
        directorio.close();
    }

    public void eliminarDocumentos() throws IOException{
        escritor.deleteAll();
    }
    
    // El texto se indexa titulo, resumen y todo
    public void indexar(String deTextoAIndexar, String deClassesEnOntologia) throws IOException {
        Document documento = new Document();

        // texto completo
        Field fieldTexto = new Field("texto", deTextoAIndexar, TextField.TYPE_STORED);
        // Nombre de las clases
        Field fieldClases = new Field("clase", deClassesEnOntologia, TextField.TYPE_STORED);

        documento.add(fieldTexto);
        documento.add(fieldClases);

        escritor.addDocument(documento);
    }

    public void indexarDocumentosEjemplo() throws IOException {
        crearEscritor();
        eliminarDocumentos();
        for (int i=1; i <=5 ; i++) {
            String nmArchivoTexto =  "texto" + i + ".txt";
            String nmArchivoClase =  "clases" + i + ".txt";
            String deTexto = retornarTexto(nmArchivoTexto);
            String deClase = retornarTexto(nmArchivoClase);
            indexar(deTexto, deClase);
        }
        cerrarEscritor();
    }

    private String retornarTexto(String nmArchivo) throws FileNotFoundException, IOException {
        String nmRuta = Util.RESFLDR;
        FileInputStream fstream = new FileInputStream (nmRuta + nmArchivo);
        DataInputStream entrada =  new DataInputStream(fstream);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
        
        String strLinea;
        String texto = "";
        
        while ((strLinea =  buffer.readLine()) !=  null) {
            texto = texto + "\n";
            texto = texto + strLinea;
        }
        
        return texto;
    }
}
