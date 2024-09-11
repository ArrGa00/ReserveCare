package untitled.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import untitled.domain.*;

@Component
public class HospitalHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Hospital>> {

    @Override
    public EntityModel<Hospital> process(EntityModel<Hospital> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() + "//initializebeds"
                )
                .withRel("/initializebeds")
        );

        return model;
    }
}
