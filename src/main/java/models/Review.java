package models;

public class Review {
    private String author;
    private int score;
    private String text;

    public Review(String author, int score, String text) {
        this.author = author;
        this.score = score;
        this.text = text;
    }

    public String getAuthor() { return author; }
    public int getScore() { return score; }
    public String getText() { return text; }
}
