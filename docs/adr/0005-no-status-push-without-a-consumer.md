# ADR 0005 — Do not publish status.json without a consumer

Status: accepted · 2026-09-09

## Context
CI committed status.json back to main after every run. That forced three
workarounds: paths-ignore so the push would not re-trigger the workflow,
[skip ci] in the commit message, and a rebase-and-retry block for when
main moved mid-run. The retry still surfaced as a failure in the logs
even when the push eventually succeeded.

Checking the site revealed that only qavant-tests and qavant-api-tests
feed the qavant.dev metrics widget. Nothing consumed this repo's
status.json. The machinery had no reader.

## Decision
Stop pushing status.json to main. Keep generating it and attach it to
the run artifacts instead. Drop paths-ignore and reduce the workflow's
permissions to contents: read.

## Consequences
+ No more races between CI and local pushes; three workarounds removed.
- Write access could NOT be dropped: actions-gh-pages needs contents:
  write to push the Allure report to gh-pages. Tried contents: read
  first, the run failed with a 403. The push to main is gone, the
  permission stays.
- status.json is now behind the Actions UI rather than at a public URL.
  If the metrics widget is ever extended to this suite, publish it to
  gh-pages next to the Allure report — not back to main.
- The other three suites still push to main. They have a real consumer,
  so the trade-off there is different and was left alone.
