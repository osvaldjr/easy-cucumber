`yourfile.feature`

```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    Given I have a request with body <REQUEST FILE PATH>
    When I make a <HTTP METHOD> to <URI>
    When I make a request defined in <PATH TO REQUEST DEFINITION FILE>
    Given my application host is <YOUR APPLICATION HOST>
```

| Parameter                         | Description                                                                    | Example                                                                                                 |
| :-------------------------------- | :----------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------ |
| `REQUEST FILE PATH`               | Contents of the file that will be sent in the request for your API             | [EXAMPLE](https://github.com/osvaldjr/easy-cucumber/wiki/Request-steps#request-file-path)               |
| `HTTP METHOD`                     | Http method to be called against your API                                      | GET\|POST\|PUT\|DELETE                                                                                  |
| `URI`                             | Your API endpoint                                                              | /api/v1/pokemon/1/pikachu                                                                               |
| `PATH TO REQUEST DEFINITION FILE` | Json file defining url, method, body, headers and query params                 | [EXAMPLE](https://github.com/osvaldjr/easy-cucumber/wiki/Request-steps#path-to-request-definition-file) |
| `YOUR APPLICATION HOST`           | Application target host, if you want a different one from your application.yml | http://localhost:9003                                                                                   |

##### Examples

- ###### REQUEST FILE PATH

  If you want to define the content in request body to your application, you should use this step to tell where the body content file is located

  ```gherkin
  Given I have a request with body http_request_body.json
  ```
  Using this step, you should put a file `http_request_body.json` in your `resources/data/YOUR_FEATURE_FILE_NAME/` folder, witch will contain data you want to be sent to your application
  After that, you can use step above to make request to your application

  ```gherkin
  Given I make a POST to /
  ```

- ###### PATH TO REQUEST DEFINITION FILE
  If you want to define your entire request at one time and execute it, you can use this step

  ```gherkin
    When I make a request defined in http_request_post.json
  ```

    Using this step, you should put a file `http_request_post.json` in your `resources/data/YOUR_FEATURE_FILE_NAME/` folder, witch will contain all information necessary to make request to your application. Easy Cucumber will load file contents and execute request.

    ```json
        {
          "url": "/test",
          "method": "POST",
          "body": {
            "name": "Linux"
          },
          "headers": {},
          "queryParams": {}
        }
    ```
- ###### PATH TO REQUEST DEFINITION FILE
  If you want to define your entire request at one time and execute it, you can use this step
