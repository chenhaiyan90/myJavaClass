package proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class Test3 {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		final MyPayGateWay alliPayGw = new MyAllipayGateway();
		MethodInterceptor methodIntceptor = new MethodInterceptor() {
			@Override
			public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy)
					throws Throwable {
				if (method.getName().equals("pay")) {
					if ((Double) args[2] > 1000) {
						System.out.println("two large .... ");
						throw new RuntimeException("Denied ，two large  " + args[2]);
					} else {
						System.out.println("check ok ");

					}
				}
				return method.invoke(alliPayGw, args);
			}
		};

		MyPayGateWay paygw = (MyPayGateWay) Enhancer.create(MyPayGateWay.class, methodIntceptor);
		paygw.pay(555, 123456, 100);
		paygw.pay(555, 123456, 2000);
	}

}
