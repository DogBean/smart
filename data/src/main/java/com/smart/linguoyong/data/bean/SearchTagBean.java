package com.smart.linguoyong.data.bean;

/**
 * Created by Dino on 1/25 0025.
 */

public class SearchTagBean {

    /**
     * element : 就业
     * score : 72
     * binaryElement : 5bCx5Lia
     */

    private String element;
    private int score;
    private String binaryElement;

    public SearchTagBean(String element, int score, String binaryElement) {
        this.element = element;
        this.score = score;
        this.binaryElement = binaryElement;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getBinaryElement() {
        return binaryElement;
    }

    public void setBinaryElement(String binaryElement) {
        this.binaryElement = binaryElement;
    }
}
