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
import java.util.Iterator;

/**
 *
 * @author martin.canaval
 */
public class Ejemplo {

    public static void main(String[] args) {
        new Ejemplo().go();
    }

    private void go() {
        Model schema = FileManager.get().loadModel("owlDemoSchema.xml");
        Model data = FileManager.get().loadModel("owlDemoData.xml");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel infmodel = ModelFactory.createInfModel(reasoner, data);

        Resource nForce = infmodel.getResource("urn:x-hp:eg/nForce");
        System.out.println("nForce *:");
        printStatements(infmodel, nForce, null, null);

        Resource gamingComputer = infmodel.getResource("urn:x-hp:eg/GamingComputer");
        Resource whiteBox = infmodel.getResource("urn:x-hp:eg/whiteBoxZX");
        if (infmodel.contains(whiteBox, RDF.type, gamingComputer)) {
            System.out.println("White box recognized as gaming computer");
        } else {
            System.out.println("Failed to recognize white box correctly");
        }

        ValidityReport validity = infmodel.validate();
        if (validity.isValid()) {
            System.out.println("OK");
        } else {
            System.out.println("Conflicts");
            for (Iterator i = validity.getReports(); i.hasNext();) {
                ValidityReport.Report report = (ValidityReport.Report) i.next();
                System.out.println(" - " + report);
            }
        }
    }

    public void printStatements(Model m, Resource s, Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }
}
