package org.bjdrgs.bjwt.core.format;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * JSON序列化java.util.Date对象为日期格式（yyyy-MM-dd）
 * @author ying
 *
 */
public class DateSerializer extends JsonSerializer<Date>{

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");   
        String formattedDate = formatter.format(value);   
        jgen.writeString(formattedDate);   
	}
}
