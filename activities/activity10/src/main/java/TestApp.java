import javax.swing.*;

public class TestApp {
    public static void main(String[] args) {
        AddProductView testView = new AddProductView();
        testView.pack();
        testView.setVisible(true);
        AddProductController testController = new AddProductController(testView);
    }
}
