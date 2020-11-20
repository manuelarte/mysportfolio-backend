package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.KitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.PlainKitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.SleevesPlainKitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.StripeKitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.VShapeKitPart;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.KitPartDto;
import org.manuel.mysportfolio.model.dtos.team.kits.PlainKitPartDto;
import org.manuel.mysportfolio.model.dtos.team.kits.SleevesPlainKitPartDto;
import org.manuel.mysportfolio.model.dtos.team.kits.StripeKitPartDto;
import org.manuel.mysportfolio.model.dtos.team.kits.VShapeKitPartDto;
import org.springframework.stereotype.Component;

@Component
public class KitPartDtoToKitPartTransformer implements Function<KitPartDto, KitPart> {

  @Override
  public KitPart apply(final KitPartDto kitPart) {
    if (kitPart instanceof PlainKitPartDto) {
      final var kit = (PlainKitPartDto) kitPart;
      return new PlainKitPart(kit.getColour());
    } else if (kitPart instanceof SleevesPlainKitPartDto) {
      final var kit = (SleevesPlainKitPartDto) kitPart;
      return new SleevesPlainKitPart(kit.getBaseColour(), kit.getSleevesColour());
    } else if (kitPart instanceof StripeKitPartDto) {
      final var kit = (StripeKitPartDto) kitPart;
      return new StripeKitPart(kit.getBaseColour(), kit.getNumberOfStripes(), kit.getStripeColour(), kit.getOrientation());
    } else if (kitPart instanceof VShapeKitPartDto) {
      final var kit = (VShapeKitPartDto) kitPart;
      return new VShapeKitPart(kit.getBaseColour(), kit.getVShapeColour());
    } else {
      return null;
    }
  }
}
