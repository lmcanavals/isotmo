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

    public static void main(String[] args) {
        new IsoMain().go();
    }

    private void go() {
        Model schema = FileManager.get().loadModel("isoTestingOntologyv15.1.xml");
        Model data = FileManager.get().loadModel("isoTestingOntologyv15.1.xml");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel infmodel = ModelFactory.createInfModel(reasoner, data);

        /*Resource alfaCapLevelOT = infmodel.getResource("http://pucp.ontology/AlfaCapLevelOT");
        System.out.println("alfa *:");
        //printStatements(infmodel, alfaCapLevelOT, null, null);*/

        Resource process = infmodel.getResource("http://pucp.ontology/OT.1OrganizationalTestProcess");
        Resource alfaOT_1 = infmodel.getResource("http://pucp.ontology/AlfaOT.1OrganizationalTestProcess");
        if (infmodel.contains(alfaOT_1, RDF.type, process)) {
            System.out.println("Alfa recognized as Proceso");
        } else {
            System.out.println("Failed to recognize Alfa correctly");
        }

        // Validación está OKAY
        /*ValidityReport validity = infmodel.validate();
        if (validity.isValid()) {
            System.out.println("OK");
        } else {
            System.out.println("Conflicts");
            for (Iterator i = validity.getReports(); i.hasNext();) {
                ValidityReport.Report report = (ValidityReport.Report) i.next();
                System.out.println(" - " + report);
            }
        }*/
    }

    public void printStatements(Model m, Resource s, Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }
}
