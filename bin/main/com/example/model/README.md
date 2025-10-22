# Model

## Role of the Model

In MVVM, the Model represents the application’s data and business logic. It is completely independent of the UI and does not know anything about JavaFX or the View.

Key points:
- Encapsulates data structures, business rules, and persistence logic.
- Provides read/write access to data, often exposing observable data that can be consumed by the ViewModel.
- Should not handle UI logic (no TextField, Button, or Label references).

## JavaFX Model Components

The Model in JavaFX is just any generic class used to hold and manipulate data. It should be completly seperate form an UI ties.