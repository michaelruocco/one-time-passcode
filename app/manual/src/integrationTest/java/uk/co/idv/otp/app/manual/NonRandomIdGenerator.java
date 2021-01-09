package uk.co.idv.otp.app.manual;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.usecases.id.IdGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class NonRandomIdGenerator implements IdGenerator {

    private static final List<UUID> DEFAULT_IDS = buildIds();

    private final AtomicInteger index = new AtomicInteger();

    private final List<UUID> ids;

    public NonRandomIdGenerator() {
        this(DEFAULT_IDS);
    }

    @Override
    public UUID generate() {
        UUID id = ids.get(index.get());
        incrementOrResetIndex();
        return id;
    }

    private void incrementOrResetIndex() {
        int value = index.incrementAndGet();
        if (value >= ids.size()) {
            index.set(0);
        }
    }

    private static List<UUID> buildIds() {
        return Arrays.asList(
                UUID.fromString("76c9ec3b-b7aa-41ae-8066-796856e71e65"),
                UUID.fromString("85bbb05a-3cf8-45e5-bae8-430503164c3b"),
                UUID.fromString("0ec36d6b-145b-4c1e-9201-1f47a8eec9a5"),
                UUID.fromString("446846e6-bf16-4da5-af5b-9ad4a240fe5d"),
                UUID.fromString("507cc493-6998-49a4-9614-38ba4296eab6")
        );
    }

}
