# View

## Role of the View
In MVVM, the View is responsible for displaying the user interface and handling user interactions visually, but it should not contain business logic. Its main job is to bind UI elements to properties in the ViewModel so that the UI reflects the state of the application automatically.

Key points:
- It observes data exposed by the ViewModel.
- It forwards user input (e.g., clicks, typing) to the ViewModel.
- It does not directly manipulate the Model.

## JavaFX View Components

### 1. FXML Files

- These define the layout of the UI
- Not found in `view` found instead under `src/main/resources/fxml`

### 2. Controller Classes

- These are the classes that bind the FXML UI controls to ViewModel properties.
- These sould be thin classes
- Often called the View code-behind