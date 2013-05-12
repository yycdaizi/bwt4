package org.bjdrgs.bjwt.core.format;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String unformatedDate= jsonParser.getText();  
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");  
        Date retVal;  
        try {  
            retVal = sdf.parse(unformatedDate);  
        } catch (ParseException e) {  
            return null;  
        }  
        return retVal;
	}

}
