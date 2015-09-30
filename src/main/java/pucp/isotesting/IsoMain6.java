package pucp.isotesting;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.RDFS;

public class IsoMain6 {

    private final String NS = "http://pucp.ontology/";
    private final String FN = "isoTestingOntologyv17.xml";

    public static void main(String[] args) {
        new IsoMain6().go();
    }

    private void go() {
        System.out.println("Iniciando: cargando ontología");
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        m.read(FN);

        System.out.println("Creando InfModel");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel im = ModelFactory.createInfModel(reasoner, m);
        Resource procOT0 = im.getResource(NS + "OT1OrganizationalTestProcess");
        Resource procOT1 = im.getResource(NS + "OT1OrganizationalTestProcessCapLevel1");
        Resource procOT2 = im.getResource(NS + "OT1OrganizationalTestProcessCapLevel2");
        Resource capLevelOT = im.getResource(NS + "CapabilityLevelOT1OrganizationalTestProcess");
        Resource pa11OT = im.getResource(NS + "PA11ProcessPerfOT1OrganizationalTestProcess");
        Resource ot1bp1 = im.getResource(NS + "OT1BP1DevelopOrganizationalTestEpecification");
        Resource ot1bp2 = im.getResource(NS + "OT1BP2MonitorAndControlUseOfOrganizationalTestSpecification");
        Resource ot1bp3 = im.getResource(NS + "OT1BP3UpdateOrganizationalTestEpecification");

        Property isAt = im.getProperty(NS + "isAt");
        Property achievedBy = im.getProperty(NS + "achievedBy");
        Property satisfiedBy = im.getProperty(NS + "satisfiedBy");
                
        System.out.println("Creando atributo BETA 1");
        Resource betaAttribute1 = im.createResource(NS + "BetaAttribute1");
        betaAttribute1.addProperty(RDFS.subClassOf, pa11OT);
        betaAttribute1.addProperty(satisfiedBy, ot1bp1);
        betaAttribute1.addProperty(satisfiedBy, ot1bp2);
        betaAttribute1.addProperty(satisfiedBy, ot1bp3);
        
        System.out.println("Creando nivel de capacidad BETA");
        Resource betaCapLevel = im.createResource(NS + "BetaCapLevel");
        betaCapLevel.addProperty(RDFS.subClassOf, capLevelOT);
        betaCapLevel.addProperty(achievedBy, betaAttribute1);
        
        System.out.println("Creando proceso BETA...");
        Resource betaProc = im.createResource(NS + "Beta");
        betaProc.addProperty(RDFS.subClassOf, procOT0);
        betaProc.addProperty(isAt, betaCapLevel);
                
        // Realizando la inferencia.
        System.out.println("Razonando...");
        if (im.contains(betaProc, RDFS.subClassOf, procOT0)) {
            System.out.println("Proceso BETA ha alcanzado nivel de capacidad 0");
        }
        if (im.contains(betaProc, RDFS.subClassOf, procOT1)) {
            System.out.println("Proceso BETA ha alcanzado nivel de capacidad 1");
        }
        if (im.contains(betaProc, RDFS.subClassOf, procOT2)) {
            System.out.println("Proceso BETA ha alcanzado nivel de capacidad 2");
        }

        System.out.println("Finalizando!");
    }

}
