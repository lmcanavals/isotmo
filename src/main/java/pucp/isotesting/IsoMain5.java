package pucp.isotesting;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.RDFS;

public class IsoMain5 {

    private final String NS = "http://pucp.ontology/";
    private final String FN = "isoTestingOntologyv17.xml";

    public static void main(String[] args) {
        new IsoMain5().go();
    }

    private void go() {
        System.out.println("Iniciando: cargando ontología");
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        m.read(FN);

        /*System.out.println("Recuperando una clase: ");
        OntClass p1 = m.getOntClass(NS + "OT1OrganizationalTestProcess");

        System.out.println("Listando subclases de : " + p1.getURI());
        for (Iterator<OntClass> i = p1.listSubClasses(); i.hasNext();) {
            OntClass c = i.next();
            System.out.println(c.getURI());
        }*/

        System.out.println("Creando InfModel");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel im = ModelFactory.createInfModel(reasoner, m);
        Resource procOT1 = im.getResource(NS + "OT1OrganizationalTestProcessCapLevel2"); // cambiar a nivel 1 y debe botar YES
        Resource procAlfa = im.getResource(NS + "AlfaOT1");
        if (procOT1 == null) {
            System.out.println(":( OT");
        }
        if (procAlfa == null) {
            System.out.println(":( Alfa");
        }
        if (im.contains(procAlfa, RDFS.subClassOf, procOT1)) {
            System.out.println("YES");
        }

        System.out.println("Finalizando!");
    }

}
