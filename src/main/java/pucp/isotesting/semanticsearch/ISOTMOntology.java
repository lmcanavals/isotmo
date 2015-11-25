/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.isotesting.semanticsearch;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author n221
 */
public class ISOTMOntology {
    private final InfModel infModel;

    public ISOTMOntology() {
        String inputSchemaName = Util.ONTOLOGYFILE;
        Model schema = FileManager.get().loadModel(inputSchemaName);
        Reasoner rsnr = ReasonerRegistry.getOWLReasoner().bindSchema(schema);
        infModel = ModelFactory.createInfModel(rsnr, schema);
    }
    
    public List<String> obtenerSubClases(String uri) {
        List<String> listaClases = new ArrayList<String>();
        Resource resource = infModel.getResource(uri);
        StmtIterator i = infModel.listStatements(null, RDFS.subClassOf, resource);
        while (i.hasNext()) {
            Statement stmt = i.nextStatement();
            listaClases.add(stmt.getSubject().getLocalName());
        }
        return listaClases;
    }
    
    public static void main(String[] args) {
        String uri = Util.NS + "Practice";
        List<String> listaClases = new ISOTMOntology().obtenerSubClases(uri);
        System.out.println(listaClases);
    }
}
