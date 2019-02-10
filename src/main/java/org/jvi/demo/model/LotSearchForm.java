package org.jvi.demo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jvi.demo.validation.constraints.Site;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class LotSearchForm {

  private boolean checkLotClient;

  @NotBlank
  private String immId;

  @Site
  private String siteId;

  private String immName;

  @NotNull
  private SearchType immNameSearchType = SearchType.A;

  private String lotId;
  private String adresse;
  private String zipCode;
  private String zipLabel;
  private String lotLabel;
  private String typeLot;
  private String genreLot;
  private String comboBatiment;
  private String batimentId;
  private String batimentName;
  private String niveauBatiment;
  private String statut;
  private String codeLotCommercial;
  private String codeLotStock;
  private String codeLotProgrammeCommercial;

  // FIXME : gerer avec un pojo
  // private String orderBy;

}
