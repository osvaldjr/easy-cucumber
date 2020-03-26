`yourfile.feature`

```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    Given I want to execute sql script <YOUR SQL SCRIPT>
    Given I want to execute sql file in path <YOUR SQL SCRIPT>
    Given the table <YOUR TABLE NAME> has the following data:
      | <COLUMN NAME 1>  | <COLUMN NAME 2> | <COLUMN NAME N> |
      | <COLUMN VALUE 1> | <COLUMN VALUE 2>| <COLUMN VALUE N>|
      | <COLUMN VALUE 1> | <COLUMN VALUE 2>| <COLUMN VALUE N>|
    Then the table <YOUR TABLE NAME> should have the following data:
      | <COLUMN NAME 1>  | <COLUMN NAME 2> | <COLUMN NAME N> |
      | <COLUMN VALUE 1> | <COLUMN VALUE 2>| <COLUMN VALUE N>|
    Then the table <YOUR TABLE NAME> is empty
```

| Parameter         | Description                                                | Example                                                                                  |
| :---------------- | :--------------------------------------------------------- | :--------------------------------------------------------------------------------------- |
| `YOUR SQL SCRIPT` | Script file name containing your sql script to run in step | [EXAMPLE](https://github.com/osvaldjr/easy-cucumber/wiki/Database-steps#your-sql-script) |
| `YOUR TABLE NAME` | Table name you want to populate or assert data             | --                                                                                       |
| `COLUMN NAME`     | Column name of your table                                  | --                                                                                       |
| `COLUMN VALUE`    | Column value of your table                                 | --                                                                                       |

##### Examples

- ###### YOUR SQL SCRIPT

  If you want to run sql script in your step definition, you should put it in your `resources/data/YOUR_FEATURE_FILE_NAME/` folder. Easy cucumber will load this file and execute it in your configured database.

  If you have a feature with name `database_assertions.feature` and want to delete some lines before run steps, then you must create a file in `resources/data/database_assertions/delete.sql` with your deletion script. And your step definition will be like:

  ```gherkin database_assertions.feature
  Given I want to execute sql script delete.sql
  ```

##### Steps

- ###### THE TABLE <YOUR TABLE NAME> SHOULD HAVE THE FOLLOWING DATA

  To validate as columns you can use regex, example: [0-9], null, [A-Za-z]{3}
  
  ![](https://i.pinimg.com/originals/27/d0/24/27d0245c348d1148a2e85a0bf2ad2c64.png)
