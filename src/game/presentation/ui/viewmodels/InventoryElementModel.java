package game.presentation.ui.viewmodels;

public class InventoryElementModel {
    private String numItems;
    private String imagePath;

    public InventoryElementModel(String numItems, String imagePath) {
        this.numItems = numItems;
        this.imagePath = imagePath;
    }

    public String getNumItems() {
        return numItems;
    }

    public void setNumItems(String numItems) {
        this.numItems = numItems;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}