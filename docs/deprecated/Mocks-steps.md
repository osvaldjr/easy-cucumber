`yourfile.feature`
```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    Given A have a mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for dependency <DEPENDENCY NAME>
    Then I expect mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for dependecy <DEPENDENCY NAME> to have been called <TIMES TO YOUR MOCK SHOULD BE CALLED> times
```

| Parameter            | Description         | Example                     |
| :----------------- | :------------- | :-------------------------------- |
|`PATH OF MOCK FILES FOR REQUEST AND RESPONSE`|Json files defining expected request and response to your API|[EXAMPLE](https://github.com/osvaldjr/easy-cucumber/wiki/Mocks-steps#path-of-mock-files-for-request-and-response)|
|`DEPENDENCY NAME`|Alias for your http dependency|pokemon-service|
|`TIMES TO YOUR MOCK SHOULD BE CALLED`|Times you expect your http dependency should be called|1|

##### Examples
- ###### `PATH OF MOCK FILES FOR REQUEST AND RESPONSE`
    Suppose you have a feature called `pokemon.feature` and your application had an GET method, wich receives a query string for search pokemons and integrates with an external dependency responsible for returning available pokemons matching your query param

    ```gherkin
    Given A have a mock pokemon-detail for dependency pokemon-service
    ```
    Using this step, you should put two files named `pokemon-detail-request.json` and `pokemon-detail-response.json` in your `resources/data/pokemon/mocks` folder. Easy cucumber will look to them in order to setup mock server for your application dependency.
    - **`pokemon` in folder path**: the name of your feature file
    - **`pokemon-detail` in request and response file names**: the parameter you entered in your step
    - **`pokemon-service` in your gherkin**: an alias for your dependency, this parameter will be prefixed with your dependency configuration in your application.yml and the result should be placed in your application configuration
        
        If in your test application.yml you had your stubby admin endpoint configured:
        ```yaml
        dependencies.stubby.url: http://localhost:8889
        ```
        Then you should put the stubby portal url as your pokemon dependency in your application.yml
        ```properties
        http://localhost:8882/pokemon-service
        ```
    
    `pokemon-detail-request.json`
    ```json
    {
      "url": "/pokemon/*",
      "method": "GET",
      "body": {},
      "headers": {
        "content-type": "application/json"
      },
      "queryParams": {"name": "pikachu"}
    }
    ```
    `pokemon-detail-response.json`
    ```json
    {
      "headers": {},
      "status": 200,
      "body": {
        "name": "Pikachu",
        "weight":7,
        "base_experience":80
      }
    }
    ```