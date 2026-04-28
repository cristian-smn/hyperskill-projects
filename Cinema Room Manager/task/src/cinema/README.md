# Cinema Manager

A console-based cinema seat reservation application built with a layered architecture
that mirrors production patterns, adapted to the scale and constraints of a CLI context.

## Architecture

### `domain`
Contains `Cinema`, the core domain object of the application. Rather than a thin model
with logic delegated to a separate service layer, `Cinema` is a *rich domain object* —
it holds both state (the seat map, ticket price) and business rules (seat validation,
purchase logic). This is a deliberate choice: at this scale, a separate service layer
would add indirection without adding value.

Also contains all custom exceptions (`InvalidSeatException`, `SeatOutOfBoundsException`,
`PurchasedSeatException`), which are part of the domain contract — they express
business rule violations, not I/O or formatting errors, and would remain unchanged
regardless of whether the UI were a console, a web interface, or a mobile app.

### `model`
Contains `CinemaConfig`, a DTO (Data Transfer Object) whose sole purpose is to carry
initialization data from the UI layer to the domain constructor. It holds no logic and
has no lifecycle beyond the construction of `Cinema`.

### `ui`
In a full production architecture, this layer would be split into Controllers, Views,
and Repositories. At this scale, each class collapses all three responsibilities into
a single coherent unit, organized by actor rather than by technical role.

`AdminConsoleUI` handles the setup phase — reading the configuration provided by the
administrator before the application starts. `UserConsoleUI` handles the runtime phase
— the interactive loop through which end users purchase seats and view the cinema map.
Both classes own their I/O entirely: they read input, apply presentation logic, and
write output. `System.in` and `System.out` act as the View layer.

### `utils`
Contains `InputUtils`, a thin wrapper around `Scanner`. In the current implementation
it adds minimal value over passing `Scanner` directly, and this is acknowledged
consciously. Its purpose is to provide a single point of change if the input source
ever needs to be replaced (e.g. switching from `Scanner` to `BufferedReader`) or if
concurrent access needs to be introduced (e.g. adding `synchronized` for multithreading)
without modifying any other class in the application.

## Entry Point

`Main` lives at the root of the package and acts as a pure bootstrap: it instantiates
all dependencies, wires them together, and delegates control to the UI layer. It contains
no business logic and no I/O of its own. The `Scanner` is created and closed here via
`try-with-resources`, ensuring the resource is managed in exactly one place regardless
of how the application exits.