package ai.logic.definite;

public class TopDownReasonerTest extends ReasonerTest {
    @Override
    Reasoner getReasoner() {
        return new TopDownReasoner();
    }
}
