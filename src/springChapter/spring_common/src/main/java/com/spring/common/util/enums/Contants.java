package com.spring.common.util.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 00013885 on 2016/11/18.
 */
public class Contants {
    public enum Fake{
        EXPENSE("EXPENSE","余额账户"),
        normal("normal","正常"),
        Courier("Courier","快递员"),
        Payment("Payment","支付"),
        Income("Income","收入"),
        WithdrawRefund("WithdrawRefund","提现退款"),
        overdue("overdue","逾期占箱"),
        deliver("deliver","投递"),
        Charge_Refund("Charge_Refund","计费系统返还"),
        APPROVE_FAIL("APPROVE_FAIL","审核失败"),
        Expenses("Expenses","支出"),
        NEED_APPROVE("NEED_APPROVE","待审核"),
        Salary("Salary","报酬"),
        FAIL("FAIL","失败"),
        RechargePresent("RechargePresent","充值赠送"),
        Customer("Customer","c端用户"),
        Recharge("Recharge","充值"),
        SUCCESS("SUCCESS","成功"),
        abnormal("abnormal","异常"),
        NEEDCHECK("NEEDCHECK","待复核"),
        Withdraw("Withdraw","提现"),
        Charge_Reward("Charge_Reward","计费系统奖励"),
        Rewards("Rewards","奖励"),
        INCOME("INCOME","收入账户"),
        medium("medium","中箱"),
        UnfreezePay("UnfreezePay","解冻扣款"),
        reservation("reservation","预约"),
        Charge_Expense("Charge_Expense","计费系统扣费"),
        NEED_COMMIT("NEED_COMMIT","待提交"),
        Refund("Refund","退款"),
        BOUNCED("BOUNCED","退票"),
        sbig("sbig","超大箱"),
        big("big","大箱"),
        Bounced("Bounced","退票"),
        small("small","小箱"),
        renewal("renewal","续约"),
        DEALING("DEALING","处理中"),
        queueReservation("queueReservation","排队预约"),
        DISCUE("DISCUE","已废弃");

        private String index;
        private String value;
        public String getIndex(){
            return index;
        }
        public String getValue(){
            return value;
        }
        Fake(String index, String value){
            this.index = index;
            this.value = value;
        }
        @Override
        public String toString(){
            return value;
        }
    }

    public static String[] contents = {"id", "uid", "orderSerialNo", "payBy", "bankCardNo", "cashFee", "status", "createTime",
            "withdrawTime", "updateTime", "batchPayId", "dealMemo", "refund"};
    public static String[] titles = {"提现ID", "用户ID", "订单号", "付款方", "收款方", "提现金额", "提现结果", "提现时间", "提现执行时间", "提现完成时间", "批次号",
            "处理描述", "退票"};

    public static Map<String, String> fakeAttr = new HashMap<String, String>() {
        {
            put("EXPENSE", "余额账户");
            put("INCOME", "收入账户");
            put("Customer", "c端用户");
            put("Courier", "快递员");
            put("APPROVE_FAIL", "审核失败");
            put("NEED_APPROVE", "待审核");
            put("NEED_COMMIT", "待提交");
            put("NEEDCHECK", "待复核");
            put("FAIL", "失败");
            put("DISCUE", "已废弃");
            put("BOUNCED", "退票");
            put("DEALING", "处理中");
            put("SUCCESS", "成功");
            put("Income", "收入");
            put("Expenses", "支出");
            put("Recharge", "充值");
            put("Rewards", "奖励");
            put("Payment", "支付");
            put("Withdraw", "提现");
            put("WithdrawRefund", "提现退款");
            put("Salary", "报酬");
            put("Refund", "退款");
            put("Bounced", "退票");
            put("RechargePresent", "充值赠送");
            put("UnfreezePay", "解冻扣款");
            put("Charge_Expense", "计费系统扣费");
            put("Charge_Refund", "计费系统返还");
            put("Charge_Reward", "计费系统奖励");
            put("deliver", "投递");
            put("reservation", "预约");
            put("queueReservation", "排队预约");
            put("renewal", "续约");
            put("overdue", "逾期占箱");
            put("small", "小箱");
            put("medium", "中箱");
            put("big", "大箱");
            put("sbig", "超大箱");
            put("normal", "正常");
            put("abnormal", "异常");
            put("contract2C","C端包柜");
            put("contract2B","B端包柜");
        }
    };
}

