package org.bjdrgs.bjwt.core.format;

import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * JSON序列化java.util.Date对象为日期时间格式（yyyy-MM-dd HH:mm:ss）
 * @author ying
 *
 */
public class DateTimeSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        String formattedDate = formatter.format(value);   
        jgen.writeString(formattedDate);   
	}

}
