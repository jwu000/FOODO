package util;

public class FavoriteAdapterItem {
    // Type could be restaurant or recipe
    String type;
    String name;

    public FavoriteAdapterItem(String type, String name){
        this.type = type;
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
