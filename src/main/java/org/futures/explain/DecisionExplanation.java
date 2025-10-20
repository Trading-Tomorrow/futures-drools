package org.futures.explain;

import java.util.Collections;
import java.util.List;

/** Simple DTO with the three views of the decision process. */
public class DecisionExplanation {
    private final List<String> how;
    private final List<String> why;
    private final List<String> whyNot;

    public DecisionExplanation(List<String> how, List<String> why, List<String> whyNot) {
        this.how = List.copyOf(how);
        this.why = List.copyOf(why);
        this.whyNot = List.copyOf(whyNot);
    }

    public List<String> getHow()    { return Collections.unmodifiableList(how); }
    public List<String> getWhy()    { return Collections.unmodifiableList(why); }
    public List<String> getWhyNot() { return Collections.unmodifiableList(whyNot); }
}
