package ksmart38.mybatis.domain;

public class Goods {
	private String goodsCode;
	private String goodsName;
	private int goodsPrice;
	private String goodsSellerId;
	private String goodsRegDate;
	private Member member;
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsSellerId() {
		return goodsSellerId;
	}
	public void setGoodsSellerId(String goodsSellerId) {
		this.goodsSellerId = goodsSellerId;
	}
	public String getGoodsRegDate() {
		return goodsRegDate;
	}
	public void setGoodsRegDate(String goodsRegDate) {
		this.goodsRegDate = goodsRegDate;
	}
	
	@Override
	public String toString() {
		return "Goods [goodsCode=" + goodsCode + ", goodsName=" + goodsName + ", goodsPrice=" + goodsPrice
				+ ", goodsSellerId=" + goodsSellerId + ", goodsRegDate=" + goodsRegDate + ", member=" + member + "]";
	}
	
}
