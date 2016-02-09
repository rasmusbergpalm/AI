package ai.reasoning;

public class BottomUpReasonerTest extends ReasonerTest {
    @Override
    Reasoner getReasoner() {
        return new BottomUpReasoner();
    }
}
