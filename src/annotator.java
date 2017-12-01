import java.util.List;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import gov.ornl.stucco.entity.CyberEntityAnnotator.CyberAnnotation;
import gov.ornl.stucco.entity.EntityLabeler;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;


public class annotator {

	public static JSONArray main(String text) {		
		
		EntityLabeler labeler = new EntityLabeler();
		String exampleText = text;
		Annotation doc = labeler.getAnnotatedDoc("My Doc", exampleText );
		
		JSONArray list = new JSONArray();
		List<CoreMap> sentences = doc.get(SentencesAnnotation.class);
		for ( CoreMap sentence : sentences) {
			for ( CoreLabel token : sentence.get(TokensAnnotation.class)) {
//				if (annotation.length() != 0) {
//			        annotation += ",\n";
//			    }
				//annotation ="{"+"text: "+ token.get(TextAnnotation.class) + "POS: " + token.get(PartOfSpeechAnnotation.class) + "CyberAnnot: " + token.get(CyberAnnotation.class)+"}";
				//annotation += "{\"text\":"+"\""+token.get(TextAnnotation.class)+"\""+", \"POS\":"+"\"" + token.get(PartOfSpeechAnnotation.class)+"\""+", \"CyberAnnot\":"+"\""+ token.get(CyberAnnotation.class)+"\"}";
				JSONObject annotation = new JSONObject();
				try {
					annotation.put("token", token.get(TextAnnotation.class));
					annotation.put("POS", token.get(PartOfSpeechAnnotation.class));
					annotation.put("CyberAnnot", token.get(CyberAnnotation.class));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				list.put(annotation);
			}
			
			//System.out.println("Parse Tree:\n" + sentence.get(TreeAnnotation.class));
		}
		return list;

	}

}
