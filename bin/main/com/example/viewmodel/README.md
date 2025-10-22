# ViewModel

# Role of the ViewModel

The ViewModel acts as an intermediary between the View and the Model:
- Exposes data from the Model in a form that the View can bind to directly.
- Handles commands or actions triggered by the View (e.g., button clicks).
- Keeps the View unaware of business logic; all logic is in the ViewModel or Model.

Key points:
- Provides observable properties for the View to bind to.
- Converts raw Model data into UI-friendly formats if needed.
- Can perform validation, formatting, or derived calculations.

# JavaFX ViewModel Components

In JavaFX ViewModel Classes should be used to bind UI interactions to properties and methods of the model classes.