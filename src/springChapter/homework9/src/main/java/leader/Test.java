package leader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url="http://localhost:9090/test2 xx";
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");
		urlBuilder.queryParam("client_id", "111");
		urlBuilder.queryParam("redirect_uri", url);
		urlBuilder.queryParam("scope", "test1 test2");
		urlBuilder.queryParam("userName", "Leader好同学");
		//OptionalValidatorFactoryBean
		String urlecnoded = urlBuilder.build(false).encode().toUriString();
		System.out.println(urlecnoded);
		MultiValueMap<String, String> map = UriComponentsBuilder.fromHttpUrl(urlecnoded).build(true).getQueryParams();
		System.out.println(map.getFirst("userName"));
		System.out.println(map.getFirst("redirect_uri"));
		System.out.println("JDK encode "+URLEncoder.encode(url, "UTF-8"));
		//MultipartResolver  
	}

}
