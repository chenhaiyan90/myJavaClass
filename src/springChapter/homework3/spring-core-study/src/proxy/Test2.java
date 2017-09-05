package proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class Test2 {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		ProxyFactory factory = new ProxyFactory();
		factory.setSuperclass(MyAllipayGateway.class);
		Class aClass = factory.createClass();
		final MyPayGateWay alliPayGw = (MyPayGateWay) aClass.newInstance();

		MethodHandler methodHandler = new MethodHandler() {
			    @Override
			    public Object invoke(Object self, Method overridden, Method proceed, Object[] args) throws Throwable {
			    	if (overridden.getName().equals("pay")) {
						if ((Double) args[2] > 1000) {
							System.out.println("two large .... ");
							throw new RuntimeException("Denied ，two large  " + args[2]);
						} else {
							System.out.println("check ok ");

						}

					}
			        return proceed.invoke(alliPayGw, args);
			
			    }
			
			};
			
			((ProxyObject)alliPayGw).setHandler(methodHandler);
        
		MyPayGateWay paygw= alliPayGw;
		paygw.pay(555, 123456, 100);
		paygw.pay(555, 123456, 2000);
	}

}
