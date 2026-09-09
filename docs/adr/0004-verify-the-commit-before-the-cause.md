# ADR 0004 — Check which commit CI tested before diagnosing a failure

Status: accepted · 2026-09-09

## Context
After fixing ADR 0001 locally, CI still showed red. Two rounds of
diagnosis followed — headless rendering, runner speed, JDK differences —
before anyone compared the run's headSha against origin/main. The failing
run had been triggered on the commit *before* the fix was merged.

## Decision
When CI is red, the first check is which commit it ran, not why it
failed:

    gh run list --workflow=<file> --limit 3 --json headSha,conclusion
    git rev-parse origin/main

## Consequences
+ Rules out the most common false alarm in about ten seconds.
- Only helps if done first; once a plausible theory exists, it is
  tempting to chase it instead.
