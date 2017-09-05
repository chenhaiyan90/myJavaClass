package proxy;

public class MyAllipayGateway implements MyPayGateWay{

	@Override
	public boolean pay(int userId, long orderId, double money) {
		//url pay ...
		System.out.println("pay suceess");
		return true;
	}

}
