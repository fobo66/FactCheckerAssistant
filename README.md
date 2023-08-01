# FactCheckerAssistant

[![Android CI](https://github.com/fobo66/FactCheckerAssistant/actions/workflows/main.yml/badge.svg)](https://github.com/fobo66/FactCheckerAssistant/actions/workflows/main.yml)

Android App to help you check claims from the internet. Currently in active development

App will give you possibility to check if certain claim is true or false by searching 
for resources that have already tested this claim. Also, app will contain a short guide
for users who want to check claims by themselves

## Build

To build the project, Fact Check API key is needed. It can be obtained [here](https://console.cloud.google.com/apis/api/factchecktools.googleapis.com/metrics).

Create a `.env` file from the sample:

```bash
cp sample.env .env
```

And insert your API key in the `.env` file. Alternatively, you can set a `FACTCHECK_API_KEY`
environment variable.
