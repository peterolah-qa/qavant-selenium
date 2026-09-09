# ADR 0003 — CI runs as a synthetic monitor against production

Status: accepted · 2026-09-09 (documenting an earlier decision)

## Context
The suite runs on a daily cron against https://qavant.dev — the live
site — rather than against a build artifact or preview deploy.

## Decision
Keep production as the default target. QAVANT_BASE_URL overrides it for
preview environments. The workflow publishes status.json and the Allure
report even when tests fail, then fails the build in a final step.

## Consequences
+ Catches content and deploy regressions that a build-time gate cannot
  see, because the site is a static deploy with no build to gate.
+ Results are always published, so a red run still produces a report.
- The suite depends on third-party uptime; an outage reads as a defect.
- A permanently red monitor stops being read. See ADR 0001 — the build
  was red for 93 runs and nobody looked.
