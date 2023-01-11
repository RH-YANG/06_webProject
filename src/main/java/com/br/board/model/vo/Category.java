package com.br.board.model.vo;

public class Category {
	
	private int categoryNo;
	private String categortName;
	
	public Category() {}

	public Category(int categoryNo, String categortName) {
		super();
		this.categoryNo = categoryNo;
		this.categortName = categortName;
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategortName() {
		return categortName;
	}

	public void setCategortName(String categortName) {
		this.categortName = categortName;
	}

	@Override
	public String toString() {
		return "Category [categoryNo=" + categoryNo + ", categortName=" + categortName + "]";
	}
	
	

}
