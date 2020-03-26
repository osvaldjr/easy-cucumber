`yourfile.feature`
```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    Given the feature <YOUR FEATURE TOGGLE NAME> is <FEATURE STATUS>
    Given the features toggle with status
      | name                       | status |
      | <YOUR FEATURE TOGGLE NAME> | ENABLE |
      | <YOUR FEATURE TOGGLE NAME> | DISABLE|
```
| Parameter            | Description         | Example                     |
| :----------------- | :------------- | :-------------------------------- |
|`YOUR FEATURE TOGGLE NAME`|Key of your feature toggle|[EXAMPLE](https://github.com/osvaldjr/easy-cucumber/wiki/Feature-toggles-steps#your-feature-toggle-name)|
|`FEATURE STATUS`|Status you expect your feature when your API will be called|ENABLE\|DISABLE|

##### Examples
- ###### YOUR FEATURE TOGGLE NAME
    Lets assume your application uses a FF4j feature with `retry-on-failure` defined key, and you want enable this feature before run some steps:

    ```gherkin
    Given the feature retry-on-failure is ENABLE
    ```
    Attention: You have to define in your test application.yml the features you want to enable and disable if using hook @DisableFeatures or @EnableFeatures, example:


    ```yaml
    features:
      names: 
      - retry-on-failure
    ```