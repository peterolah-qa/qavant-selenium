// Reads TestNG's results XML and writes a compact status.json that the
// qavant.dev site can fetch. Same contract as the other qavant suites:
// { passed, total, passRate, runs, lastRun }.
import { readFileSync, writeFileSync, existsSync } from 'node:fs';

const candidates = [
  'target/surefire-reports/testng-results.xml',
  'target/failsafe-reports/testng-results.xml',
];
const file = candidates.find(existsSync);
if (!file) {
  console.error('testng-results.xml not found — did the Maven test phase run?');
  process.exit(0); // never fail the build over a missing report
}

const xml = readFileSync(file, 'utf8');
const attr = (name) => {
  const m = xml.match(new RegExp(`<testng-results[^>]*\\b${name}="(\\d+)"`));
  return m ? Number(m[1]) : 0;
};

const total = attr('total');
const passed = attr('passed');
const passRate = total ? Math.round((passed / total) * 1000) / 10 : 0;

let runs = 0;
try { runs = JSON.parse(readFileSync('status.json', 'utf8')).runs || 0; } catch {}

const status = { passed, total, passRate, runs: runs + 1, lastRun: new Date().toISOString() };
writeFileSync('status.json', JSON.stringify(status, null, 2) + '\n');
console.log('status.json:', status);
