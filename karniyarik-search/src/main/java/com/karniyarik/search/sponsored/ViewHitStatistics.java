package com.karniyarik.search.sponsored;

public class ViewHitStatistics {

	private int hit = 0;
	private int view = 0;

	public ViewHitStatistics() {

	}

	public ViewHitStatistics(int hit, int view) {
		this.hit = hit;
		this.view = view;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}
}
