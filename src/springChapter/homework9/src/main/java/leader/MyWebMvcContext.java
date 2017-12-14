package leader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcContext extends WebMvcConfigurerAdapter {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new MyDateConverter("yyyy-MM-dd"));
	}

	final class MyDateConverter implements Converter<String, Date> {

		private final SimpleDateFormat formatter;

		public MyDateConverter(String dateFormat) {
			this.formatter = new SimpleDateFormat(dateFormat);
		}

		@Override
		public Date convert(String source) {
			if (source == null || source.isEmpty()) {
				return null;
			}

			try {
				return formatter.parse(source);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
