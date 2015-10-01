package pucp.isotesting;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Container;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

public class IsoMain8 {

    private final String NS = "http://pucp.ontology/";
    private final String FN = "isoTestingOntologyv17.xml";

    public static void main(String[] args) {
        new IsoMain8().go();
    }

    private void go() {
        System.out.println("Iniciando: cargando ontología");
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        m.read(FN);

        OntClass procOT0 = m.getOntClass(NS + "OT1OrganizationalTestProcess");
        OntClass procOT1 = m.getOntClass(NS + "OT1OrganizationalTestProcessCapLevel1");
        OntClass procOT2 = m.getOntClass(NS + "OT1OrganizationalTestProcessCapLevel2");
        OntClass capLevelOT = m.getOntClass(NS + "CapabilityLevel1OT1OrganizationalTestProcess");
        OntClass pa11OT = m.getOntClass(NS + "PA11ProcessPerformanceOT1OrganizationalTestProcess");
        OntClass ot1bp1 = m.getOntClass(NS + "OT1BP1DevelopOrganizationalTestEpecification");
        OntClass ot1bp2 = m.getOntClass(NS + "OT1BP2MonitorAndControlUseOfOrganizationalTestSpecification");
        OntClass ot1bp3 = m.getOntClass(NS + "OT1BP3UpdateOrganizationalTestEpecification");

        Property isAt = m.getProperty(NS + "isAt");
        Property achievedBy = m.getProperty(NS + "achievedBy");
        Property satisfiedBy = m.getProperty(NS + "satisfiedBy");
        
        RDFList aux;
        OntClass aux2;
        
        System.out.println("Creando atributo BETA 1");
        Resource betaAttribute1 = m.createClass(NS + "BetaAttribute1");
        aux  = m.createList();
        aux.addProperty(RDFS.subClassOf, pa11OT);
        aux.addProperty(satisfiedBy, ot1bp1);
        aux.addProperty(satisfiedBy, ot1bp2);
        aux.addProperty(satisfiedBy, ot1bp3);
        betaAttribute1.addProperty(OWL.intersectionOf, aux);
        
        System.out.println("Creando nivel de capacidad BETA");
        Resource betaCapLevel = m.createClass(NS + "BetaCapLevel");
        aux  = m.createList();
        aux.addProperty(RDFS.subClassOf, capLevelOT);
        aux.addProperty(achievedBy, betaAttribute1);
        betaCapLevel.addProperty(OWL.intersectionOf, aux);
        
        System.out.println("Creando proceso BETA...");
        Resource betaProc = m.createClass(NS + "Beta");
        aux  = m.createList();
        aux.addProperty(RDFS.subClassOf, procOT0);
        aux.addProperty(isAt, betaCapLevel);
        betaProc.addProperty(OWL.intersectionOf, aux);
                
        System.out.println("Creando InfModel");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel im = ModelFactory.createInfModel(reasoner, m);
        
        // Realizando la inferencia.
        System.out.println("Razonando...");
        if (im.contains(betaProc, OWL.equivalentClass, procOT0) || im.contains(betaProc, RDFS.subClassOf, procOT0)) {
            System.out.println("Proceso BETA ha alcanzado nivel de capacidad 0");
        }
        if (im.contains(betaProc, OWL.equivalentClass, procOT1) || im.contains(betaProc, RDFS.subClassOf, procOT1)) {
            System.out.println("Proceso BETA ha alcanzado nivel de capacidad 1");
        }
        if (im.contains(betaProc, OWL.equivalentClass, procOT2) || im.contains(betaProc, RDFS.subClassOf, procOT2)) {
            System.out.println("Proceso BETA ha alcanzado nivel de capacidad 2");
        }

        System.out.println("Finalizando!");
    }

}
