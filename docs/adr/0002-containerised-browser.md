# ADR 0002 — Run the browser in a pinned container

Status: accepted · 2026-09-09

## Context
Two failures appeared when reproducing a CI result locally, neither
related to the test under investigation:

- a stale chromedriver 131 on PATH against Chrome 152 — every session
  died with SessionNotCreated before the browser opened
- a local JDK 26 against a project targeting 17 — aspectjweaver could
  not read the newer bytecode and Allure reporting stopped silently

Neither reproduced in CI, and neither was visible from CI.

## Decision
Run the browser in selenium/standalone-chromium, pinned to 4.27.0 to
match the Java client. DriverFactory connects via RemoteWebDriver when
SELENIUM_REMOTE_URL is set, and falls back to local Chrome otherwise.
CI starts the Grid from the same docker-compose.yml used locally, so
the pinned version has a single source of truth.

## Consequences
+ Browser and client versions are locked together; the drift class of
  failure above is eliminated.
+ Contributors without Docker can still run the suite via the fallback.
- Chromium, not Chrome: Google does not build Chrome for Linux on ARM,
  so a multi-arch image means the browser is close to, but not identical
  to, production Chrome.
- Two supported paths means two paths that can break.
