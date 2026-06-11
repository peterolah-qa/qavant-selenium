# qavant-selenium

Selenium 4 + TestNG UI suite for [qavant.dev](https://qavant.dev) — the same site is also
covered by a [Playwright suite](https://github.com/peterolah-qa/qavant-tests) (TypeScript)
and a [Newman API suite](https://github.com/peterolah-qa/qavant-api-tests). One system,
three technologies, three independent CI pipelines.

## Stack

Java 17 · Selenium 4.x (Selenium Manager — no driver juggling) · TestNG · Maven

## Design decisions

- **Page Object Model** — tests never locate elements directly; locators live in
  `pages/HomePage.java`, once.
- **Explicit waits only** — no `Thread.sleep()` anywhere. Every action waits for
  exactly the condition it needs (`pages/BasePage.java`).
- **One fresh browser per test** — full isolation, no shared state (`core/BaseTest.java`).
- **CI-first** — headless by default; `-Dheadless=false` to watch locally.
- **Boot-overlay aware** — the site shows a boot animation that intercepts clicks
  while fading; the page object waits it out before any interaction.

## Run it

```bash
mvn -B test                                   # against https://qavant.dev
mvn -B test -Dqavant.baseUrl=https://preview  # against a preview deploy
mvn test -Dheadless=false                     # watch the browser
```

## CI & live metrics

GitHub Actions runs the suite on every push and daily at 07:00. After each run it
publishes a `status.json` (passed / total / pass rate / run count) back to `main` —
the qavant.dev metrics widget reads files like this one to show **real** CI results.
