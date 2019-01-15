package entity;


public class Dictionary {


    public Dictionary() {
    }

    public Dictionary(Long id, String word, String description) {
        this.id = id;
        this.word = word;
        this.description = description;
    }


    private Long id;

    private String word;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}