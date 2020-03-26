`yourfile.feature`
```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    Given I import context from open API specification "<PATH OPEN API>"
    Given I exclude urls from scan
      | url             |
      | http://.*/api.* |
      | http://.*/v2.*  |
      | http://.*/error |
    Given I generate the proxy session
    Given I generate security test HTML report with name "<REPORT NAME>"
    Given I remove all alerts
    When I remove alerts
      | url             |
      | http://.*/api.* |
      | http://.*/v2.*  |
      | http://.*/error |
    When I import scan policy "<POLICY NAME>" from file "<POLICY FILE>"
    When I run active scan
    Then the number of risks per category should not be greater than  
      | low | medium | high | informational |
      | 1   | 0      | 0    | 0             |
```
| Parameter            | Description         | 
| :----------------- | :------------- | 
|`PATH OPEN API`|URI Open API, consider in path the target url _${target.url}/v2/api-docs_. Example: **"/v2/api-docs"** |200|
|`REPORT NAME`|Report name. The reports generated in folder _target/security-reports/_.|
|`POLICY NAME`|Name of the policy to be imported.|
|`POLICY FILE`|Name of the policy file. This policy should be in the folder _src/test/resources/policy/defaut.policy_. Example: **"defaut.policy"**.<br>Generate policy steps:<br>1. Open ZAP;<br>2. Select **Analyse** menu option and select **Scan Policy Manager**;<br>3. Create your policy, select and export file;|

##### Steps

- ###### I GENERATE THE PROXY SESSION

  The session generate in folder _target/security-session/_.

# Known issues
- **Step "remove alerts" do not update data report**<br>
Because ZAP runs in daemon mode, removing existing alerts does not update the report.