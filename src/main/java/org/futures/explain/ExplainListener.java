package org.futures.explain;

import org.kie.api.event.rule.*;
import java.util.*;

/**
 * Minimal ExplainListener
 * - HOW/WHY: free-form notes you call from DRL via explain.how()/explain.why()
 * - WHY NOT: collected from agenda stats (created/fired/cancelled)
 */
public class ExplainListener implements AgendaEventListener, RuleRuntimeEventListener {

    // --- WHERE we collect text notes  ---
    private final List<String> howNotes = new ArrayList<>();
    private final List<String> whyNotes = new ArrayList<>();

    // --- WHY NOT stats ---
    private final Map<String, Integer> createdPerRule   = new HashMap<>();
    private final Map<String, Integer> firedPerRule     = new HashMap<>();
    private final Map<String, List<MatchCancelledCause>> cancelledPerRule = new HashMap<>();

    // Current rule being executed (so helpers can prefix messages)
    private final ThreadLocal<String> currentRule = new ThreadLocal<>();

    // ===== Helpers to be called from DRL =====
    /** Add a HOW note (how the decision was reached). Use inside rule THEN. */
    public void how(String message) {
        String rule = Optional.ofNullable(currentRule.get()).orElse("APP");
        howNotes.add("[" + rule + "] " + message);
    }

    /** Add a WHY note (why we took that decision). Use inside rule THEN. */
    public void why(String message) {
        String rule = Optional.ofNullable(currentRule.get()).orElse("APP");
        whyNotes.add("[" + rule + "] " + message);
    }

    // ===== Agenda events (WHY NOT + track current rule) =====
    @Override public void matchCreated(MatchCreatedEvent e) {
        createdPerRule.merge(e.getMatch().getRule().getName(), 1, Integer::sum);
    }
    @Override public void matchCancelled(MatchCancelledEvent e) {
        cancelledPerRule.computeIfAbsent(e.getMatch().getRule().getName(), k -> new ArrayList<>())
                .add(e.getCause());
    }
    @Override public void beforeMatchFired(BeforeMatchFiredEvent e) {
        currentRule.set(e.getMatch().getRule().getName());
    }
    @Override public void afterMatchFired(AfterMatchFiredEvent e) {
        firedPerRule.merge(e.getMatch().getRule().getName(), 1, Integer::sum);
        currentRule.remove();
    }

    // Unused AgendaEventListener methods
    @Override public void agendaGroupPopped(AgendaGroupPoppedEvent e) {}
    @Override public void agendaGroupPushed(AgendaGroupPushedEvent e) {}
    @Override public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent e) {}
    @Override public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent e) {}
    @Override public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent e) {}
    @Override public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent e) {}

    // ===== RuleRuntimeEventListener (not used here, but we implement the interface) =====
    @Override public void objectInserted(ObjectInsertedEvent e) {}
    @Override public void objectUpdated(ObjectUpdatedEvent e) {}
    @Override public void objectDeleted(ObjectDeletedEvent e) {}

    // ===== Build a compact result object =====
    public DecisionExplanation build() {
        // WHY NOT summary per rule
        Set<String> rules = new HashSet<>();
        rules.addAll(createdPerRule.keySet());
        rules.addAll(firedPerRule.keySet());
        rules.addAll(cancelledPerRule.keySet());

        List<String> whyNot = new ArrayList<>();
        for (String r : rules) {
            int created = createdPerRule.getOrDefault(r, 0);
            int fired   = firedPerRule.getOrDefault(r, 0);
            List<MatchCancelledCause> canc = cancelledPerRule.getOrDefault(r, List.of());

            if (created == 0) {
                whyNot.add(String.format("[WHY NOT] \"%s\": conditions never satisfied.", r));
            } else if (fired == 0) {
                whyNot.add(String.format("[WHY NOT] \"%s\": had matches but never fired. Causes=%s", r, canc));
            } else {
                whyNot.add(String.format("[WHY NOT] \"%s\": fired %d time(s) (matches created=%d).", r, fired, created));
            }
        }
        Collections.sort(whyNot);

        return new DecisionExplanation(howNotes, whyNotes, whyNot);
    }
}
