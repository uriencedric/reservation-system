package com.asset.reservation.asset.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Asset {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull(message = "Asset _id cannot be null")
  private String _id;

  @NotNull(message = "Asset Name cannot be null")
  private String assetName;

  @NotNull(message = "Asset Qty cannot be null")
  private long assetQuantity;

  @NotNull(message = "AssetType cannot be null")
  @ManyToOne(targetEntity = AssetType.class, fetch = FetchType.EAGER)
  private AssetType assetType;
}
