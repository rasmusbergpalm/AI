package ai.logic.definite;

public class BottomUpReasonerTest extends ReasonerTest {
    @Override
    Reasoner getReasoner() {
        return new BottomUpReasoner();
    }
}
