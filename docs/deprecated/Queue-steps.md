`yourfile.feature`

```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    Given I put message <FILE MESSAGE> in queue <QUEUE NAME>
    When I consume message from queue <QUEUE NAME>
    Then message contains property <JSON PATH>
    And message does not contains property <JSON PATH>
    And message property <JSON PATH> has value <EXPECT VALUE WITH JSON PATH>
    And message match with value from file <FILE MESSAGE>
```

| Parameter         | Description                                                | Example                                                                                  |
| :---------------- | :--------------------------------------------------------- | :--------------------------------------------------------------------------------------- |
| `FILE MESSAGE` | File name containing your message queue | [EXAMPLE](https://github.com/osvaldjr/easy-cucumber/wiki/Queue-steps#file-message) |
| `QUEUE NAME` | Queue name you want to put or consume message             | --                                                                                       |
| `JSON PATH`     | Json path of your property                                  | --                                                                                       |
| `EXPECT VALUE WITH JSON PATH`    | Value you expect to return in json path | --                                                                                       |

##### Examples

- ###### FILE MESSAGE

  If you have a feature with name `queues_assertions.feature` and want to put message in queue before run steps, then you must create a file in `resources/data/queues_assertions/messages/data.message`. And your step definition will be like:

  ```gherkin database_assertions.feature
  Given I put message data.message in queue users
  ```
