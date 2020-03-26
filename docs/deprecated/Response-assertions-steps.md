`yourfile.feature`

```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    Then I expect to receive a <HTTP STATUS> status
    Then I expect to receive a <HTTP STATUS> status with body <RESPONSE BODY FILE PATH>
    Then I expect <RESPONSE BODY FILE PATH> as response
    Then response contains property <JSON PATH> with value <VALUE>
    Then response does not contain property <JSON PATH>
```

| Parameter                 | Description                                                       | Example                                                                                                     |
| :------------------------ | :---------------------------------------------------------------- | :---------------------------------------------------------------------------------------------------------- |
| `HTTP STATUS`             | Http status expected to be returned by your API                   | 200                                                                                                         |
| `RESPONSE BODY FILE PATH` | Contents of the response body expected to be returned by your API | [EXAMPLE](https://github.com/osvaldjr/easy-cucumber/wiki/Response-assertions-steps#response-body-file-path) |
| `JSON PATH`               | JSON path used to find property at response                       | `[0].request.json.name`                                                                                     |
| `VALUE`                   | Value expected in property `JSON PATH`                            | `Linux`                                                                                                     |

##### Examples

- ###### RESPONSE BODY FILE PATH

  With this step you can define the expected response that your API should return

  ```gherkin
  Then I expect http_response_body_expected.json as response
  ```

  Using this step, you should put a file `http_response_body_expected.json` in your `resources/data/YOUR_FEATURE_FILE_NAME/` folder. Easy cucumber will load file contents and match against the data your API returned
