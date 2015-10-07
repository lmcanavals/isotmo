package pucp.isotesting.temp;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class IsoMain3 {

    private final String NS = "http://pucp.ontology/";
    private final String FN = "/home/martin/Archive/isotmo/src/main/resources/isoTestingOntologyv15.2.xml";
    
    public static void main(String[] args) throws OWLOntologyCreationException {
        new IsoMain3().go();
    }

    private void go() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o = m.loadOntologyFromOntologyDocument(IRI.create(FN));
        Reasoner hermit = new Reasoner(o);
        System.out.println(hermit.isConsistent());
    }

}
