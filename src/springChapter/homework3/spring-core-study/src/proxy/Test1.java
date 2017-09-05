package proxy;

import proxy.MyPayGateWayFactory.PayGateWayType;

public class Test1 {

	public static void main(String[] args) {
		MyPayGateWay paygw=MyPayGateWayFactory.getPayGateWay(PayGateWayType.Alipay);
		paygw.pay(555, 123456, 100);
		paygw.pay(555, 123456, 2000);
	}

}
