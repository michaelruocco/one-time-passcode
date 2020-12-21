package uk.co.idv.otp.usecases.send;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.otp.entities.send.LoadContextRequest;

public interface ContextLoader {

    Context load(LoadContextRequest request);

}
