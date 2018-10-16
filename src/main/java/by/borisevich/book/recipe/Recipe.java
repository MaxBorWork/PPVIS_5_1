package by.borisevich.book.recipe;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "Recipe")
public class Recipe {

    @Id
    @GeneratedValue
    private int id;

    private String title;
    private String category;
    private String citeUrl;

    @ElementCollection
    @CollectionTable(name = "Ingredients", joinColumns = @JoinColumn(name = "recipe_id",
                        referencedColumnName = "id"))
    @MapKeyColumn(name = "key_column")
    @Column(name = "value_column")
    private Map<String, String> ingredientsMap = new HashMap<String, String>();
    private String textInstruction;
    private int colOfPerson;
    private String cookingTime;

    public Recipe(String title, String category, String citeUrl, Map<String, String> ingredientsMap,
                  String textInstruction, int colOfPerson, String cookingTime) {
        this.title = title;
        this.category = category;
        this.citeUrl = citeUrl;
        this.ingredientsMap = ingredientsMap;
        this.textInstruction = textInstruction;
        this.colOfPerson = colOfPerson;
        this.cookingTime = cookingTime;
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCiteUrl() {
        return citeUrl;
    }

    public void setCiteUrl(String citeUrl) {
        this.citeUrl = citeUrl;
    }

    public Map<String, String> getIngredientsMap() {
        return ingredientsMap;
    }

    public void setIngredientsMap(Map<String, String> ingredientsMap) {
        this.ingredientsMap = ingredientsMap;
    }

    public String getTextInstruction() {
        return textInstruction;
    }

    public void setTextInstruction(String textInstruction) {
        this.textInstruction = textInstruction;
    }

    public int getColOfPerson() {
        return colOfPerson;
    }

    public void setColOfPerson(int colOfPerson) {
        this.colOfPerson = colOfPerson;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
