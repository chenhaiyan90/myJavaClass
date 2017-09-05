package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MyPayGateWayFactory {
	public enum PayGateWayType {
		Alipay, Aeixinpay
	}

	private static final Map<PayGateWayType, MyPayGateWay> allPayGateWay;
	static {
		allPayGateWay = new HashMap<PayGateWayType, MyPayGateWay>();

		MyAllipayGateway originAliPayGw = new MyAllipayGateway();
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getName().equals("pay")) {
					if ((Double) args[2] > 1000) {
						System.out.println("two large .... ");
						throw new RuntimeException("Denied ，two large  " + args[2]);
					} else {
						System.out.println("check ok ");

					}

				}
				return method.invoke(originAliPayGw, args);
			}
		};

		MyPayGateWay proxiedGateWay = (MyPayGateWay) Proxy.newProxyInstance(MyAllipayGateway.class.getClassLoader(),
				new Class[] { MyPayGateWay.class }, invocationHandler);
		Util.genProxy2file(MyAllipayGateway.class);
		allPayGateWay.put(PayGateWayType.Alipay, proxiedGateWay);
	}

	public static MyPayGateWay getPayGateWay(PayGateWayType gateWayType) {
		return allPayGateWay.get(gateWayType);
	}
}
