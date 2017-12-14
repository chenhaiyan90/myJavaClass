package com.spring.demo5.testThreadlocal;

/**
 * Created by 00013810 on 2017/9/29.
 */

    class TestClient extends Thread
    {
        private SequenceNumber sn;
        public TestClient(SequenceNumber sn) {
            this.sn = sn;
        }
        public void run()
        {
            for (int i = 0; i < 3; i++) {//④每个线程打出3个序列值
                System.out.println("thread["+Thread.currentThread().getName()+
                        "] sn["+sn.getNextNum()+"]");
                Thread t = Thread.currentThread();
            }
        }

        public static void main(String[] args)
        {
            SequenceNumber sn = new SequenceNumber();
//③ 3个线程共享sn，各自产生序列号
            TestClient t1 = new TestClient(sn);
            TestClient t2 = new TestClient(sn);
            TestClient t3 = new TestClient(sn);
            t1.start();
            t2.start();
            t3.start();
        }
    }

