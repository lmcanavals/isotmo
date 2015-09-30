package pucp.isotesting;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import java.util.Iterator;

public class IsoMain2 {

    private final String NS = "http://pucp.ontology/";
    private final String FN = "isoTestingOntologyv15.2.xml";

    public static void main(String[] args) {
        new IsoMain2().go();
    }

    private void go() {
        System.out.println("Iniciando: cargando ontología");
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        m.read(FN);
        
        System.out.println("Recuperando una clase: ");
        OntClass p1 = m.getOntClass(NS + "OT.1OrganizationalTestProcessCapLevel1");
        
        System.out.println("Listando subclases de : " + p1.getURI());
        for (Iterator<OntClass> i = p1.listSubClasses(); i.hasNext();) {
            OntClass c = i.next();
            System.out.println(c.getURI());
        }
        
        System.out.println("Creando InfModel");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel im = ModelFactory.createInfModel(reasoner, m);
        Resource r = im.getResource(NS + "OT.1OrganizationalTestProcessCapLevel1");
        StmtIterator it = r.listProperties();
        while (it.hasNext()) {
            System.out.println(": " + it.next());
        }

        System.out.println("Finalizando!");
    }

}
