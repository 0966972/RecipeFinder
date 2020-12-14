# RecipeFinder

**Project Setup Instructions**
* clone the project with the link provided by Github.
* After the files have been transferred, run `mvnw install` from your IDE's terminal or in the commandline from the project's root directory.
    * A local instance of Node.js and npm will be installed inside the project's directory.
    * Node modules will be installed.
* We recommend adding two Run configurations to your IDE (such as Intellij IDEA): the Angular application and the Spring Application.
    * Add the Angular application by selecting a npm template, pointing at `package.json` in the root directory. The command should be `run` and the script to run should be `npm start`.
    * Start the Spring Application by running the main class: `nl.hr.recipefinder.RecipeFinderApplication`.
* Once started, by default the front- and backend will be available at:
    * http://localhost:4200/ for Angular
    * http://localhost:8080/ for Spring Application
