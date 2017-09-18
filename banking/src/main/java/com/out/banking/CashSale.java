package com.out.banking;

public class CashSale {
	/**
	 * 
	 * B(1-Y-C) - A(1+R-Y) = AQ
	 * 1 - 0.3 - 0.003    1 + 0.003 - 0.3
	 * 0.697B - 0.703A = AQ
	 * 
	 * B = ( AQ + 0.703A ) / 0.697 = (Q + 0.703)A/0.697
	 * 
	 * A = 0.697 / (Q + 0.703)
	 * 
	 * ======================================================
	 * 
	 * 10000 10000 * 0.003 = 买入价（1 + 0.003）= 成本
	 * 卖出价 * 0.003 = 手续费
	 * (卖出价 - 买入价) * 0.3 = 盈利回扣额
	 * 卖出价 - 手续费 - 盈利回扣额 = 实际最终额度
	 * 
	 * 实际最终额度 - 成本 = 实际盈利部分
	 * 
	 * 100 * 实际盈利部分 / 成本 = 期望增长百分比
	 * 
	 * 卖出价 - 卖出手续费 - (卖出价 - 买入价) * 回扣率 - 买入价（1 + 买入手续费）
	 * 
	 */
	// 期望增长
	private static double[] expectArr = {0.1, 0.15, 0.2, 0.3, 0.5, 0.6, 0.65, 0.7, 0.8, 0.9, 1, 1.1, 1.2, 1.5};
	// 买入价
	private static double[] buyingRateArr = {5544, 9533.33, 6325, 4500};
	// 手续费率
	private static double fee = 0.003;
	// 回扣率
	private static double rebate = 0.3;
	// 买入价
	private static double[] sellingPriceArr = {6390, 6789, 14572, 15482};
	
	// 打印买入，期望增长, 卖出，扣手续，盈利
	private static void showSellingPrice() {
		for (double buying : buyingRateArr) {
			System.out.println("\n*****买入价： " + buying + "*****");
			for (double expect : expectArr) {
				double sellingPrice = (double)(buying * (expect + expect * fee + 1 + fee - rebate)) / (1 - fee - rebate);
				double feeTotal = sellingPrice*(fee + rebate) - buying*rebate;
				double grossMargin = sellingPrice - feeTotal - buying * (1 + fee);
				double balance = sellingPrice - feeTotal;
				@SuppressWarnings("unused")
				double expectTmp = (double)(grossMargin/buying * (1 + fee));
				/*System.out.println("期望增长： "+(int)(expect*100)+"%, 卖出价="+(int)Math.ceil(sellingPrice)
					+", 赎回手续费+盈利回扣费： " + (int)Math.ceil(feeTotal) + ", 实际盈利="+(int)Math.floor(grossMargin));*/
				
				System.out.println("期望增长： "+(int)(expect*100)+"%, 卖出价="+(int)Math.ceil(sellingPrice)
				+"("+(int)Math.floor(grossMargin)+"), 余额： "+(int)Math.floor(balance)+", 赎回手续费+盈利回扣费： " + (int)Math.ceil(feeTotal));
			}
		}
	}
	
	// 打印期望卖出价， 期望增长，买入价，扣手续，盈利，余额
	private static void showBuyingRate() {
		for (double selling : sellingPriceArr) {
			System.out.println("\n*****卖出价： " + selling + "*****");
			for (double expect : expectArr) {
				double buying = (double)(selling * (1 - fee - rebate) / (expect + expect * fee - rebate + 1 + fee));
				double feeTotal = selling*(fee + rebate) - buying*rebate;
				double grossMargin = selling - feeTotal - buying * (1 + fee);
				double balance = selling - (int)Math.ceil(feeTotal);
				
				System.out.println("期望增长： "+(int)(expect*100)+"%, 买入价="+(int)Math.floor(buying)
				+"("+(int)Math.floor(grossMargin)+"), 余额： "+(int)Math.floor(balance)+", 赎回手续费+盈利回扣费： " + (int)Math.ceil(feeTotal));
				
			}
		}
	}
	
	public static void main(String[] args) {
		showSellingPrice();
		showBuyingRate();
	}
}