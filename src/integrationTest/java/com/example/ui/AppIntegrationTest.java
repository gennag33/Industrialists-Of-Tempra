package com.example.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.example.view.App;

import javafx.scene.control.Button;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class AppIntegrationTest {

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) throws Exception {
        // Launch the main application
        new App().start(stage);
    }

     @Test
    void shouldSwitchFromPrimaryToSecondary(FxRobot robot) {
        // Ensure primary button is visible
        Assertions.assertThat(robot.lookup("#primaryButton").queryAs(Button.class))
                .hasText("Switch to Secondary View");

        // Click the button -> should switch to Secondary.fxml
        robot.clickOn("#primaryButton");

        // Verify secondary scene is now showing
        Assertions.assertThat(robot.lookup("#secondaryButton").queryAs(Button.class))
                .hasText("Switch to Primary View");
    }

    @Test
    void shouldSwitchFromSecondaryBackToPrimary(FxRobot robot) {
        // Go to Secondary first
        robot.clickOn("#primaryButton");

        // Verify we’re on Secondary scene
        Assertions.assertThat(robot.lookup("#secondaryButton").queryAs(Button.class)).isNotNull();

        // Click secondary button -> should switch back to Primary.fxml
        robot.clickOn("#secondaryButton");

        // Verify Primary scene is showing again
        Assertions.assertThat(robot.lookup("#primaryButton").queryAs(Button.class))
                .hasText("Switch to Secondary View");
    }
}