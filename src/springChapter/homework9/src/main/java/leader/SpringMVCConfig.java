package leader;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonpHttpMessageConverter4;
import com.alibaba.fastjson.support.spring.FastJsonpResponseBodyAdvice;

//@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {
    @Bean
    public FastJsonpResponseBodyAdvice fastJsonpResponseBodyAdvice() {
    	//JsonView
        return new FastJsonpResponseBodyAdvice("callback", "jsonp");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new FastJsonpHttpMessageConverter4());
        super.extendMessageConverters(converters);
    }
}