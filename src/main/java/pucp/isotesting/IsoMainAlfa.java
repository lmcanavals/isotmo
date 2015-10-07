package pucp.isotesting;

import pucp.isotesting.temp.*;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

public class IsoMainAlfa {

    private final String NS = "http://pucp.ontology/";
    private final String FN = "isoTestingOntologyv17.xml";

    public static void main(String[] args) {
        new IsoMainAlfa().go();
    }

    private void go() {
        System.out.println("Iniciando: cargando ontología");
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        m.read(FN);

        System.out.println("Creando InfModel");
        OntClass procOT0 = m.getOntClass(NS + "OT1OrganizationalTestProcess");
        OntClass procOT1 = m.getOntClass(NS + "OT1OrganizationalTestProcessCapLevel1");
        OntClass procOT2 = m.getOntClass(NS + "OT1OrganizationalTestProcessCapLevel2");
        
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel im = ModelFactory.createInfModel(reasoner, m);
        Resource procAlfa = im.getResource(NS + "AlfaOT1");
        if (procOT1 == null) {
            System.out.println("No se encontro el proceso OT...");
        }
        if (procAlfa == null) {
            System.out.println("No se encontro el proceso ALFA...");
        }
        if (im.contains(procAlfa, OWL.equivalentClass, procOT0) || im.contains(procAlfa, RDFS.subClassOf, procOT0)) {
            System.out.println("Proceso ALFA ha alcanzado nivel de capacidad 0");
        }
        if (im.contains(procAlfa, OWL.equivalentClass, procOT1) || im.contains(procAlfa, RDFS.subClassOf, procOT1)) {
            System.out.println("Proceso ALFA ha alcanzado nivel de capacidad 1");
        }
        if (im.contains(procAlfa, OWL.equivalentClass, procOT2) || im.contains(procAlfa, RDFS.subClassOf, procOT2)) {
            System.out.println("Proceso ALFA ha alcanzado nivel de capacidad 2");
        }

        System.out.println("Finalizando!");
    }

}
