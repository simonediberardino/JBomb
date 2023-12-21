package game.ui.pages;

import game.data.DataInputOutput;
import game.events.models.RunnablePar;
import game.ui.frames.BombermanFrame;
import game.ui.panels.menu.AvatarMenuPanel;
import game.ui.panels.menu.UsernameProfilePanel;
import game.utils.Paths;
import game.utils.XMLUtils;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public abstract class AbstractMainMenuPanel extends BaseMenu {
    public AbstractMainMenuPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame);
    }

    @Override
    protected JPanel getRightPanel() {
        String[] avatarPaths = XMLUtils.parseXmlArray(Paths.getSkinsXml(), "skins");
        assert avatarPaths != null;

        RunnablePar getSkinRunnable = new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                return Paths.getEntitiesFolder() + "/player/" + DataInputOutput.getInstance().getSkin();
            }
        };

        // Define a Consumer to handle the skin change
        Consumer<Integer> skinChangeConsumer = indexAdder -> {
            String currAvatar = (String) getSkinRunnable.execute(null);

            int newIndex = 0;
            for (int i = 0; i < avatarPaths.length; i++) {
                String avatarPath = avatarPaths[i];
                if (currAvatar.endsWith("/" + avatarPath)) {
                    newIndex = i + indexAdder;
                    break;
                }
            }

            if (newIndex >= avatarPaths.length) {
                newIndex = 0;
            } else if (newIndex < 0) {
                newIndex = avatarPaths.length - 1;
            }

            DataInputOutput.getInstance().setSkin(avatarPaths[newIndex]);
        };

        return new AvatarMenuPanel(getSkinRunnable, skinChangeConsumer);
    }

    @Override
    protected JPanel getLeftPanel() {
        RunnablePar getUsernameRunnable = new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                return DataInputOutput.getInstance().getUsername();
            }
        };

        // Define a Consumer to handle the username change
        Consumer<String> usernameChangeConsumer = username -> {
            if (username.isBlank()) return;
            DataInputOutput.getInstance().setUsername(username.trim());
        };

        return new UsernameProfilePanel(getUsernameRunnable, usernameChangeConsumer);
    }

    @Override
    public void onShowCallback() {

    }
}