package org.jvi.demo.model;

import java.time.Instant;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(force = true)
@Data
@AllArgsConstructor
public class Evenement {

  private String nom;
  private String numero;

  @Column(name = "client_id")
  private int idDuClient;
  private Instant debut;

}
