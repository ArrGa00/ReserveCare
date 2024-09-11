package untitled.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import untitled.domain.*;

@Component
public class HospitalizationInfoHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<HospitalizationInfo>> {

    @Override
    public EntityModel<HospitalizationInfo> process(
        EntityModel<HospitalizationInfo> model
    ) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/approve")
                .withRel("approve")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/discharge")
                .withRel("discharge")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/reject")
                .withRel("reject")
        );

        return model;
    }
}
