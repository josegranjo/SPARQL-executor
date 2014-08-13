SPARQL-executor
===============

#### Simple semantic web application for executing consistency validation queries between individuals of the Business Model Canvas, e3value and ArchiMate ontologies in RDF

This simple application was used to demonstrate my [Master thesis](https://fenix.tecnico.ulisboa.pt/publico/department/theses.do?method=showThesisDetails&selectedDepartmentUnitID=1911260507896&thesisID=2353642489841&_request_checksum_=6d7113cb90680edcfee234e23ebcd9ea078d6918)

Business Model Canvas, e3value and ArchiMate allow the modelling of business under different perspectives. Those perspectives were integrated  using ontology techniques (OWL).

The app uses the [Jena framework](https://jena.apache.org) to execute SPARQL queries on individuals of the three ontologies. Here, it is only included a e3value OWL ontology. Additional OWL ontologies were obtained for other authors.

The SPARQL queries are implemented accordingly to the mapping rules defined in the thesis.

---

Ontologies and individuals in the RDF format must be stored in `data/`. 

The generated report is a group of raw SPARQL results and it is created in `report.txt`

