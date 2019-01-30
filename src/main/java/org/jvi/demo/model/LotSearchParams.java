package org.jvi.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class LotSearchParams {
  private String opener;
  private String avType;
  private String method;
  private String type;
  private String withProto;

  @JsonProperty("ger_man_id")
  private String germanId;

  @JsonProperty("locataire_id")
  private String locataireId;

  @JsonProperty("man_id_trl")
  private String manIdtrl;

  @JsonProperty("man_proto_id")
  private String manProtoId;

  @JsonProperty("copro_id")
  private String coproId;

  private String proprio;
  private String lots;

  @JsonProperty("man_id_rss")
  private String manIdRss;

  private String modeRechLot;
  private String rechImplant;

  // imm_id, proto_id, ctype_code,
  // do_date_entree, do_date_sortie, do_id, parent_name, proto_id
  private FilterCtx filterctx;

  @Value
  @AllArgsConstructor(onConstructor = @__(@JsonCreator))
  @Builder
  public static class FilterCtx {
    private String immLotId;
  }
}
