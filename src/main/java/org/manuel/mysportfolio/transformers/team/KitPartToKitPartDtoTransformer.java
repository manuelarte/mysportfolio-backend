package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.KitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.PlainKitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.SleevesPlainKitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.StripeKitPart;
import io.github.manuelarte.mysportfolio.model.documents.team.kits.VShapeKitPart;
import io.github.manuelarte.mysportfolio.model.dtos.team.KitPartDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.kits.PlainKitPartDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.kits.SleevesPlainKitPartDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.kits.StripeKitPartDto;
import io.github.manuelarte.mysportfolio.model.dtos.team.kits.VShapeKitPartDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class KitPartToKitPartDtoTransformer implements Function<KitPart, KitPartDto> {

  @Override
  public KitPartDto apply(final KitPart kitPart) {
    if (kitPart instanceof PlainKitPart) {
      final var kit = (PlainKitPart) kitPart;
      return new PlainKitPartDto(kit.getColour());
    } else if (kitPart instanceof SleevesPlainKitPart) {
      final var kit = (SleevesPlainKitPart) kitPart;
      return new SleevesPlainKitPartDto(kit.getBaseColour(), kit.getSleevesColour());
    } else if (kitPart instanceof StripeKitPart) {
      final var kit = (StripeKitPart) kitPart;
      return new StripeKitPartDto(
          kit.getBaseColour(),
          kit.getNumberOfStripes(),
          kit.getStripeColour(),
          StripeKitPartDto.StripeOrientationDto.valueOf(kit.getOrientation().name()));
    } else if (kitPart instanceof VShapeKitPart) {
      final var kit = (VShapeKitPart) kitPart;
      return new VShapeKitPartDto(kit.getBaseColour(), kit.getVShapeColour());
    } else {
      return null;
    }
  }
}
