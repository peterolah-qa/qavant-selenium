# ADR 0001 — Page objects scroll before asserting visibility

Status: accepted · 2026-09-09

## Context
qavant.dev reveals below-the-fold content with an IntersectionObserver.
Those elements start at opacity 0 and only become visible once scrolled
into the viewport. BasePage documented this and provided scrollTo(), but
HomePage.certBadge() used visible(). The reveal never fired, the wait
timed out after 10s, and the suite was red for 93 consecutive runs.

The element was correct throughout — href, target and rel all valid.
The assertions were never reached.

## Decision
Any page-object accessor for content below the fold uses scrollTo(),
not visible(). visible() is reserved for elements present in the
initial viewport.

## Consequences
+ The suite tests what a user actually sees, not what the DOM contains.
- The rule lives in a base-class comment and this ADR; nothing enforces
  it. A skipped call produces a deterministic false negative, not a
  flaky test — which is why this one survived 93 runs unnoticed.
- Enforcement (a lint rule, or making visible() private) is open.
