package com.asset.reservation.asset.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetDto {
  private String _id;
  private String assetName;
  private long assetQuantity;
}
