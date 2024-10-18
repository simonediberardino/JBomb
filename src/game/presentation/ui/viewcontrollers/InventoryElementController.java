package game.presentation.ui.viewcontrollers;

import game.presentation.ui.viewelements.inventory.InventoryElementView;
import game.utils.Utility;
import game.presentation.ui.viewmodels.InventoryElementModel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class InventoryElementController {
    private final InventoryElementModel model;
    private final InventoryElementView view;

    public InventoryElementController(int numItems, String imagePath) {
        this(String.valueOf(numItems), imagePath);
    }

    public InventoryElementController(String numItems, String imagePath) {
        model = new InventoryElementModel(numItems, imagePath);
        view = new InventoryElementView();
        updateView();
    }

    public void setNumItems(int numItems) {
        setNumItems(String.valueOf(numItems));
    }

    public void setNumItems(String numItems) {
        model.setNumItems(Objects.equals(numItems, String.valueOf(Integer.MAX_VALUE)) ? "∞" : numItems);
        updateView();
    }

    public void setImagePath(String imagePath) {
        model.setImagePath(imagePath);
        updateView();
    }

    private void updateView() {
        Image img = Utility.INSTANCE.loadImage(model.getImagePath());
        ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(InventoryElementView.SIZE, InventoryElementView.SIZE, 0));
        view.setIcon(imageIcon);
        view.setNumItems(model.getNumItems());
    }

    public InventoryElementView getView() {
        return view;
    }
}
