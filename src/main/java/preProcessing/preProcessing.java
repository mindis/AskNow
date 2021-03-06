package preProcessing;

import init.configuration;
import utils.sendRequest;

public class preProcessing {
/*
 * Controls all pre-processing. The current implementation of the preProcessing ssteps include 
 * 			Resolving apostrophe : Who was John F. Kennedy's vice president? -->Who was  vice president of John F. Kennedy? 
 * 			Normalizing nonWh queries.	
 * The code does not hit the resolve apostrophy server. 
 * 
 * */
	
	public String preProcessingOrchestrator(String question){
		//TODO:Add more logics and control here
		try {
//			String resolveApostropheQuestion =  resolveApostrophe(question);
			String resolveApostropheQuestion = question;
			return normalizeNonWh(resolveApostropheQuestion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		//TODO: Add other pre-processing rules here.
	}
	
	public String resolveApostrophe (String question) throws Exception{
		
		//For the exact algorithm check the apostrophe_server file in resource folder. The implementation need English spacy model.
		
		sendRequest requester = new sendRequest();
		configuration config = new configuration();
		String urlParameters = "question=" + question;
		String processedQuestion = requester.sendPostRequest(config.getPythonMicroserviceUrl(), urlParameters);
		return processedQuestion;
		
	}
	
	public String normalizeNonWh(String question){
		//resolving nonWH queries to WH queries. For example :- "Give me a list of", "What is list of"
		question = question.replaceAll("(?)Give me a list of", "What is list of");
		question = question.replaceAll("(?)Give me all", "What is list of");
		question = question.replaceAll("(?)Give a list of", "What is list of");
		question = question.replaceAll("(?)Give all", "What is list of");
		question = question.replaceAll("(?)List all", "What is list of");
		question = question.replaceAll("(?)List", "What is list of");
		question = question.replaceAll("(?)Show me", "What is list of");
		
//	Using regex to circumvent the lowercase problem 
		
		return question;
	}
}
