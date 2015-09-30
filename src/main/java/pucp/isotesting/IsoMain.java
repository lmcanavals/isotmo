/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.isotesting;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 *
 * @author martin.canaval
 */
public class IsoMain {
    private final String ONTO_URI = "http://pucp.ontology/";

    public static void main(String[] args) {
        new IsoMain().go();
    }

    private void go() {
        Model model = FileManager.get().loadModel("isoTestingOntologyv15.2.xml");
        
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infmodel = ModelFactory.createInfModel(reasoner, model);

        Resource process = infmodel.getResource(ONTO_URI + "OT.1OrganizationalTestProcess");
        Resource alfaOT_1 = infmodel.getResource(ONTO_URI + "AlfaOT.1OrganizationalTestProcess");
        if (infmodel.contains(alfaOT_1, RDF.type, process)) {
            System.out.println("Alfa recognized as Proceso");
        } else {
            System.out.println("Failed to recognize Alfa correctly");
        }

    }

    public void printStatements(Model m, Resource s, Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }
}
