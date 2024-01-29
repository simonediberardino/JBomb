package game.engine.ui.viewmodels;

public class InventoryElementModel {
    private int numItems;
    private String imagePath;

    public InventoryElementModel(int numItems, String imagePath) {
        this.numItems = numItems;
        this.imagePath = imagePath;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}