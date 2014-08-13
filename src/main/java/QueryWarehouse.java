package main.java;

/*
*	Maintains the list of queries that validate consistency between ontology individuals.
*/
public class QueryWarehouse {
	
	private String[] questions;
	private String[] warehouse;
	
	public QueryWarehouse() {
		int i = 1;
		warehouse = new String[50];
		questions = new String[50];
		
		//	1
		questions[i] = "Is there any customer segment or key partner that does not exist in e3value?";
		String query = "SELECT ?cs ?kp " +
				"WHERE { " +
					"{ ?kp rdf:type bmc:Actor . " +
						"MINUS { ?kp rdf:type bmc:Actor . " +
							"?e3actor rdf:type e3value:e3Actor . " +
							"?kp integrated:corresponds ?e3actor . }} " +
					"UNION { ?cs rdf:type bmc:CustomerSegment . " +
						"MINUS { ?cs rdf:type bmc:CustomerSegment . " +
								"?e3actor rdf:type e3value:e3Actor . " +
								"?actor integrated:corresponds ?e3actor . }}}";
		warehouse[i++] = query;
		
		//	2
		questions[i] = "Is there any e3Actor that does not exist in any BMC?";
		query = "SELECT ?e3actor " +
				"WHERE { ?e3actor rdf:type e3value:e3Actor . " +
					"MINUS { ?kp rdf:type bmc:Actor . " +
						"?e3actor ^integrated:corresponds ?kp . } " +
					"MINUS { ?cs rdf:type bmc:CustomerSegment . " +
						"?e3actor ^integrated:corresponds ?cs . }}";
		warehouse[i++] = query;
		
		//	3
		questions[i] = "Is there any Actor in e3value that is not represented in Archimate?";
		query = "SELECT ?e3actor " +
				"WHERE { ?e3actor rdf:type e3value:e3Actor . " +
					"MINUS { ?ba rdf:type archimate:BusinessActor . " +
					"?e3actor integrated:corresponds ?ba . }}";
		warehouse[i++] = query;
		
		//	4
		questions[i] = "Is there any Business Actor in Archimate that is not represented in e3value?";
		query = "SELECT ?businessActor " +
				"WHERE { ?businessActor rdf:type archimate:BusinessActor . " +
					"MINUS { ?e3actor rdf:type e3value:e3Actor . " +
					"?businessActor ^integrated:corresponds ?e3actor . }}";
		warehouse[i++] = query;
		
		//	5
		questions[i] = "Is there any revenue streams not represented in e3value?";
		query = "SELECT ?revenueStream " +
				"WHERE { ?revenueStream rdf:type bmc:RevenueStream . " +
					"MINUS { ?vt rdf:type e3value:ValueTransmission . " +
					"?revenueStream integrated:corresponds ?vt . }}";
		warehouse[i++] = query;
		
		//	6
		questions[i] = "Is there any key activity not represented in e3value and not represented in Archimate?";
		query = "SELECT ?kaNOe3value ?kaNOarchimate " +
				"WHERE { " +
					"{ ?kaNOe3value rdf:type bmc:Activity . " +
						"MINUS { ?va rdf:type e3value:ValueActivity . " +
								"?kaNOe3value integrated:corresponds ?va . }} " +
					"UNION " +
					"{ ?kaNOarchimate rdf:type bmc:Activity . " +
						"?va rdf:type e3value:ValueActivity . " +
						"?kaNOarchimate integrated:corresponds ?va . " +
						"MINUS { ?bs rdf:type archimate:BusinessProcess . " +
								"?kaNOarchimate integrated:corresponds ?bs . }}}";
		warehouse[i++] = query;
		
		//	7
		questions[i] = "Is there any value activity not represented in any BMC and not in Archimate?";
		query = "SELECT ?valueActivityNObmc ?valueActivityNOarch " +
				"WHERE { " +
					"{ ?valueActivityNObmc rdf:type e3value:ValueActivity .	" +
						"MINUS { ?ka rdf:type bmc:Activity .	" +
								"?valueActivityNObmc ^integrated:corresponds ?ka . }} " +
					"UNION { ?valueActivityNOarch rdf:type e3value:ValueActivity . " +
						"MINUS { ?bp rdf:type archimate:BusinessProcess . " +
						"?valueActivityNOarch integrated:corresponds ?bp . }}}";
		warehouse[i++] = query;
		
		//	8
		questions[i] = "Is there any business process not represented in e3value and any BMC model?" ;
		query = "SELECT ?businessProcessNObmc ?businessProcessNOe3value " +
				"WHERE  { " +
					"{ ?businessProcessNObmc rdf:type archimate:BusinessProcess . " +
						"MINUS { ?ka rdf:type bmc:Activity . " +
							"?va rdf:type e3value:ValueActivity . " +
							"?businessProcessNObmc ^integrated:corresponds ?va . " +
							"?va ^integrated:corresponds ?ka . }} " +
					"UNION { ?businessProcessNOe3value rdf:type archimate:BusinessProcess . " +
						"MINUS { ?va rdf:type e3value:ValueActivity . " +
							"?businessProcessNOe3value ^integrated:corresponds ?va . }}}";
		warehouse[i++] = query;
		
		//	9
		questions[i] = "Is there any value transmission without a business service related to it?";
		query = "SELECT ?valueTransmission " +
				"WHERE { ?valueTransmission rdf:type e3value:ValueTransmission . " +
					"MINUS { ?bs rdf:type archimate:BusinessService . " +
							"?valueTransmission integrated:corresponds ?bs . }}";
		warehouse[i++] = query;
		
		//	10
		questions[i] = "Is there any business service in archimate without any value transmission related to it?";
		query = "SELECT ?businessService " +
				"WHERE { ?businessService rdf:type archimate:BusinessService . " +
					"MINUS { ?vt rdf:type e3value:ValueTransmission . " +
							"?businessService ^integrated:corresponds ?vt . }}";
		warehouse[i++] = query;
		
		//	11
		questions[i] = "Is there any Value transmission not related to the delivery of a value proposition or related to a key activity?";
		query = "SELECT ?valueTransmission " +
				"WHERE { ?valueTransmission rdf:type e3value:ValueTransmission . " +
					"MINUS { ?kp rdf:type bmc:Actor . " +
						"?ka rdf:type bmc:Activity . " +
						"?ValueTransmission 	integrated:getsValueFrom ?kp ; integrated:getsValueWith ?ka . } " +
					"MINUS { ?vp rdf:type bmc:ValueProposition . " +
						"?dc rdf:type bmc:DistributionChannel . " +
						"?cs rdf:type bmc:CustomerSegment . " +
						"?valueTransmission 	integrated:transmitsValue ?vp ; integrated:transmitsValueTo ?cs ; integrated:transmitsValueThrough ?dc . }}";
		warehouse[i++] = query;
		
		//	12
		questions[i] = "Is there any missing combination of key activity and key partner that may be mapped to a value transmission?";
		query = "SELECT ?keyActivity ?keyPartner " +
				"WHERE { ?keyActivity rdf:type bmc:Activity . " +
					"?keyPartner rdf:type bmc:Actor . " +
					"?bmc rdf:type integrated:BusinessModel . " +
					"?bmc integrated:owns ?keyActivity . " +
					"?bmc integrated:owns ?keyPartner . " +
					"MINUS { ?vt rdf:type e3value:ValueTransmission . " +
						"?keyActivity ^integrated:getsValueWith ?vt . " +
						"?keyPartner ^integrated:getsValueFrom ?vt . }}";
		warehouse[i++] = query;
		
		//	13
		questions[i] = "Is there any missing combination of value proposition, customer segment and distribution channel that may be mapped to a value transmission?";
		query = "SELECT ?valueProposition ?customerSegment ?distChannel " +
				"WHERE { ?valueProposition rdf:type bmc:ValueProposition . " +
					"?customerSegment rdf:type bmc:CustomerSegment . " +
					"?distChannel rdf:type bmc:DistributionChannel . " +
					"?bmc rdf:type integrated:BusinessModel . " +
					"?bmc integrated:owns ?valueProposition . " +
					"?bmc integrated:owns ?customerSegment . " +
					"?bmc integrated:owns ?distChannel . " +
					"MINUS { ?vt rdf:type e3value:ValueTransmission . " +
						"?valueProposition ^integrated:transmitsValue ?vt . " +
						"?customerSegment ^integrated:transmitsValueTo ?vt . " +
						"?distChannel ^integrated:transmitsValueThrough ?vt . }}";
		warehouse[i++] = query;
		
		//	14 
		questions[i] = "Is there any non correspondence between key resources and value objects?";
		query = "SELECT ?keyResource ?valueObject " +
				"WHERE {{ ?keyResource rdf:type bmc:Resource . " +
					"MINUS { ?valueObject rdf:type e3value:ValueObject . " +
					"?keyResource integrated:corresponds ?valueObject . }} " +
					"UNION { ?valueObject rdf:type e3value:ValueObject . " +
						"MINUS { ?keyResource rdf:type bmc:Resource . " +
						"?keyResource integrated:corresponds ?valueObject . }}}";
		warehouse[i++] = query;
		
		//	15
		questions[i] = "Is there any value object not represented as a value or a business object?";
		query = "SELECT ?valueObject " +
				"WHERE { ?valueObject rdf:type e3value:ValueObject . " +
					"MINUS { ?val rdf:type archimate:Value . " +
							"?valueObject integrated:corresponds ?val . } " +
					"MINUS { ?bo rdf:type archimate:BusinessObject . " +
							"?valueObject integrated:corresponds ?bo . }}";
		warehouse[i++] = query;
		
		// 16
		questions[i] = "Is there any Value or any Business Object not represented as a Value Object?";
		query = "SELECT ?value ?businessObject " +
				"WHERE {" +
					"{ ?value rdf:type archimate:Value . " +
						"MINUS { ?vo rdf:type e3value:ValueObject . " +
								"?value ^integrated:corresponds ?vo . }} " +
					"UNION { ?businessObject rdf:type archimate:BusinessObject . " +
						"MINUS { ?vo rdf:type e3value:ValueObject . " +
						"?businessObject ^integrated:corresponds ?vo . }}}";
		warehouse[i++] = query;
		
		//	17
		questions[i] = "Is there a channel not represented in Archimate?";
		query = "SELECT ?distChannel " +
				"WHERE { ?distChannel rdf:type bmc:DistributionChannel . " +
					"MINUS { ?bi rdf:type archimate:BusinsessInterface . " +
							"?distChannel integrated:corresponds ?bi . }}";
		warehouse[i++] = query;
		
		//	18
		questions[i] = "Is there any Business Interface not represented in any BMC?";
		query = "SELECT ?interface " +
				"WHERE { ?interface rdf:type archimate:BusinsessInterface . " +
					"MINUS { ?dc rdf:type bmc:DistributionChannel . " +
							"?interface ^integrated:corresponds ?dc . }}";
		warehouse[i++] = query;
		
	}
	
	public String getQuestion(int number) {
		return questions[number];
	}
	
	public String getQuery(int number) {
		return warehouse[number];
	}
	
	public int getSize() {
		return warehouse.length;
	}
}
