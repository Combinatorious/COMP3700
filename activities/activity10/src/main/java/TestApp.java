import javax.swing.*;

public class TestApp {
    public static void main(String[] args) {
        MainMenuView testView = new MainMenuView();
        testView.pack();
        testView.setVisible(true);
        MainMenuController testController = new MainMenuController(testView);
    }
}
