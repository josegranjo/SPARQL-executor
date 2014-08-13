package main.java;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;

/*
*	High-level code: loading of ontologies and queries
*/
public class App {

	public static void main(String[] args) {
		OntModel model = ModelFactory.createOntologyModel();
		OntDocumentManager docManager = model.getDocumentManager();
		
		// Read file with the ontologies and individuals in RDF format
		// Must be named ontologies.owl
		System.out.println("# Started importing ontologies #");
		model.read("data/ontologies.owl");
		System.out.println("# Done #");
		
		// report.txt is the output file containing the SPARQL results
		QueryWarehouse warehouse = new QueryWarehouse();
		QueryExecutor executor = new QueryExecutor(model, "report.txt");

		// Run consistency validation queries
		System.out.println("# Started running consistency validation queries");
		for (int i = 1; i < warehouse.getSize(); i++) {
			if(warehouse.getQuestion(i) != null) 
				executor.query(warehouse.getQuery(i), warehouse.getQuestion(i));
		}
		System.out.println("# Done #");
	}
}

