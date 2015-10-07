package pucp.isotesting.temp;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

public class IsoMain7 {

    private final String NS = "http://pucp.ontology/";
    private final String FN = "isoTestingOntologyv17.xml";

    public static void main(String[] args) {
        new IsoMain7().go();
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
        
        System.out.println("Creando atributo BETA 1");
        Resource betaAttribute1 = m.createClass(NS + "BetaAttribute1");
        betaAttribute1.addProperty(OWL.equivalentClass, pa11OT);
        betaAttribute1.addProperty(satisfiedBy, ot1bp1);
        betaAttribute1.addProperty(satisfiedBy, ot1bp2);
        betaAttribute1.addProperty(satisfiedBy, ot1bp3);
        
        System.out.println("Creando nivel de capacidad BETA");
        Resource betaCapLevel = m.createClass(NS + "BetaCapLevel");
        betaCapLevel.addProperty(OWL.equivalentClass, capLevelOT);
        betaCapLevel.addProperty(achievedBy, betaAttribute1);
        
        System.out.println("Creando proceso BETA...");
        Resource betaProc = m.createClass(NS + "Beta");
        betaProc.addProperty(OWL.equivalentClass, procOT0);
        betaProc.addProperty(isAt, betaCapLevel);
                
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
