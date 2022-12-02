#Asset Reservation Service

The Asset Reservation System allows employees to reserve available assets.
This project use Maven to handle dependencies and build.

## Run the project: 

Run `mvn clean install` to install the project dependencies.

Run `mvn test` to run the test suite.

Run `mvn spring-boot:run` to run the CLI application. It will install a h2 db @  the ~/ folder on your computer.

An other path can be specified within the application.yml file.
The project make use of the data.sql (in resources && generated in advance) as a seed, and is set to create-drop
by default. This was made for the purpose of this exercise only and avoid inserting duplicates when restarting the application.

## Available commands

Type in : "help" to display available commands. 

```
Asset Command
      * book: Reserve an asset
      * cancel: Cancel your reservation.
      * catalog: Print a paginated catalog of assets
      * reservation: Display a list of your reservations

Auth Command
        close: Exit application.
      * create-user: Create user. This command is available to managers ONLY.
        login: Log-in and initialize your reservation session.
        logout: Disconnect from session.

Built-In Commands
        help: Display help about available commands.
```

The command marked with * require authentication. Here is a list of available demo users (username/password):
- alice/demo (MANAGER) 
- terry/demo (SENIOR)
- john/demo (SENIOR)
- bob/demo (JUNIOR)

Alice can display the create-user stub command (being a manager), to demonstrate access control.

To book an asset, first run `catalog` to print a list of assets. 
The list is using pagination, so the prompt will also ask you to enter a page number . 
By default, it will display the first page and the laptop asset. You can display only the following 3 types :
 - laptop
 - screen
 - charger
 
 Notes : You cannot book for more than 365 days by default. I added this use case as it was not specified. The minimum duration is 
 one day.
 
 You can then use the asset `_id` attribute to book then get a reservation `_id` (you can see your reservations by typing `reservation`). 
 To cancel, specify the reservation `_id` to cancel & free up the asset for the other users.
 

