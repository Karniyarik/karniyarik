package com.karniyarik.categorizer.tagger;

import java.util.ArrayList;
import java.util.List;

public class NewWord {
	private String word;
	private String representation;
	private List<NewWord> similarWords = new ArrayList<NewWord>();
	private int count;
	
	public NewWord(String word, String representation, int count) {
		this.word =  word;
		this.representation = representation;
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
	}

	public List<NewWord> getSimilarWords() {
		return similarWords;
	}

	public void setSimilarWords(List<NewWord> similarWords) {
		this.similarWords = similarWords;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void addSimilarWord(NewWord newWord) {
		getSimilarWords().add(newWord);
		count += newWord.getCount();		
	}
}
