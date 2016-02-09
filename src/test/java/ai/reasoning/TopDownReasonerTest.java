package ai.reasoning;

public class TopDownReasonerTest extends ReasonerTest {
    @Override
    Reasoner getReasoner() {
        return new TopDownReasoner();
    }
}
