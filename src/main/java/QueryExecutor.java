package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

/*
*	Encapsulates the execution of queries.
*/
public class QueryExecutor {
	
	// Check that these SPARQL prefixes are accordingly to the ontologies 
	private static String PREFIXES = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>" + 
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + 
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX e3value: <http://www.semanticweb.org/granjo/ontologies/2014/1/untitled-ontology-14#>" +
			"PREFIX integrated: <http://www.semanticweb.org/granjo/ontologies/2014/3/untitled-ontology-74#>" +
			"PREFIX archimate: <https://timbus.teco.edu/ontologies/DIO.owl#>" +
			"PREFIX bmc: <http://www.owl-ontologies.com/unnamed.owl#> \n";

	private OntModel model;
	private File file;
	private int questionCounter;
	
	public QueryExecutor(OntModel model, String reportName) {
		this.model = model;
		this.questionCounter = 1;
		
		this.file = new File(reportName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void query(String queryString, String question) {
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(this.file, true);
			Query query = QueryFactory.create(PREFIXES + queryString);
			
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results =  qe.execSelect();
			
			fos.write(("[QUESTION " + questionCounter++ + "] " + question + "\n").getBytes());
			ResultSetFormatter.out(fos, results, query);
			fos.write("\n\n".getBytes());
			
			fos.flush();
			fos.close();
			qe.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
