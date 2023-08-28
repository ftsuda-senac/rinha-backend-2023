package ftsuda.rinhabackend.model;

import java.util.Arrays;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// https://www.vinsguru.com/r2dbc-entity-callback/
@Component
public class PersonEntityCallback implements AfterConvertCallback<Pessoa>, BeforeConvertCallback<Pessoa> {

    private static final String STACK_SEPARATOR = ",";

    private static final Logger log = LoggerFactory.getLogger(PersonEntityCallback.class);

    @Override
    public Publisher<Pessoa> onAfterConvert(Pessoa entity, SqlIdentifier table) {
        if (StringUtils.isNotBlank(entity.getStackDb())) {
            entity.setStack(Arrays.asList(entity.getStackDb().split(STACK_SEPARATOR)));
        }
        // log.debug(entity.toString());
        return Mono.just(entity);
    }

    @Override
    public Publisher<Pessoa> onBeforeConvert(Pessoa entity, SqlIdentifier table) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getStack() != null && entity.getStack().size() > 0) {
            entity.setStackDb(String.join(STACK_SEPARATOR, entity.getStack()));
        }
        // log.debug(entity.toString());
        return Mono.just(entity);
    }

}
